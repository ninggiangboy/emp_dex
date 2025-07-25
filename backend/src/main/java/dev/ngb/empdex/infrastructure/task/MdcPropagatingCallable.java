package dev.ngb.empdex.infrastructure.task;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

public class MdcPropagatingCallable<T> implements Callable<T> {
    private final Callable<T> task;
    private final Map<String, String> contextMap;

    public MdcPropagatingCallable(Callable<T> task, Map<String, String> contextMap) {
        this.task = task;
        this.contextMap = contextMap;
    }

    @Override
    public T call() throws Exception {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        try {
            restoreContext();
            return task.call();
        } finally {
            restorePreviousContext(previous);
        }
    }

    private void restoreContext() {
        if (contextMap != null) {
            MDC.setContextMap(contextMap);
        } else {
            MDC.clear();
        }
    }

    private void restorePreviousContext(Map<String, String> previous) {
        if (previous != null) {
            MDC.setContextMap(previous);
        } else {
            MDC.clear();
        }
    }
}
