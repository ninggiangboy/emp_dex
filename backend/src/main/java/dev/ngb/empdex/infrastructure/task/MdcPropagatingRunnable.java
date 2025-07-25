package dev.ngb.empdex.infrastructure.task;

import org.slf4j.MDC;

import java.util.Map;

public class MdcPropagatingRunnable implements Runnable {
    private final Runnable task;
    private final Map<String, String> contextMap;

    public MdcPropagatingRunnable(Runnable task, Map<String, String> contextMap) {
        this.task = task;
        this.contextMap = contextMap;
    }

    @Override
    public void run() {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        try {
            restoreContext();
            task.run();
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
