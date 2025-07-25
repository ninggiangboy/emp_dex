package dev.ngb.empdex.infrastructure.tracking;

import dev.ngb.empdex.shared.core.helper.RequestIdHelper;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class RequestIdHelperImpl implements RequestIdHelper {
    private static final String REQUEST_ID_KEY = "requestId";

    public String getRequestId() {
        return MDC.get(REQUEST_ID_KEY);
    }

    public void setRequestId(String requestId) {
        MDC.put(REQUEST_ID_KEY, requestId);
    }

    public void clear() {
        MDC.remove(REQUEST_ID_KEY);
    }
}
