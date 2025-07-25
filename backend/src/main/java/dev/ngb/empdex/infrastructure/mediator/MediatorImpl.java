package dev.ngb.empdex.infrastructure.mediator;

import dev.ngb.empdex.shared.core.base.Result;
import dev.ngb.empdex.shared.core.mediator.Mediator;
import dev.ngb.empdex.shared.core.mediator.Request;
import dev.ngb.empdex.shared.core.mediator.RequestHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MediatorImpl implements Mediator {
    private final Map<Class<?>, RequestHandler<?, ?>> handlers = new HashMap<>();

    public MediatorImpl(List<RequestHandler<?, ?>> handlerBeans) {
        for (RequestHandler<?, ?> handler : handlerBeans) {
            Class<?> handlerClass = handler.getClass();
            ParameterizedType parameterizedType = null;
            // Walk up the class hierarchy to find the parameterized interface
            for (Class<?> current = handlerClass; current != null; current = current.getSuperclass()) {
                for (Type iface : current.getGenericInterfaces()) {
                    if (iface instanceof ParameterizedType pt) {
                        if (pt.getRawType() instanceof Class &&
                                RequestHandler.class.isAssignableFrom((Class<?>) pt.getRawType())) {
                            parameterizedType = pt;
                            break;
                        }
                    }
                }
                if (parameterizedType != null) break;
            }
            if (parameterizedType == null) {
                throw new RuntimeException("Could not determine generic type for handler: " + handlerClass);
            }
            Class<?> requestType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            handlers.put(requestType, handler);
        }
    }

    @SuppressWarnings("unchecked")
    public <R, T extends Request<R>> Result<R> send(T request) {
        RequestHandler<T, R> handler = (RequestHandler<T, R>) handlers.get(request.getClass());
        if (handler == null) {
            throw new RuntimeException("No handler found for: " + request.getClass());
        }
        return handler.handle(request);
    }
}
