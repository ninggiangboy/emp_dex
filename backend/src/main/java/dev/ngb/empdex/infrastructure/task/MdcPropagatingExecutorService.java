package dev.ngb.empdex.infrastructure.task;

import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MdcPropagatingExecutorService implements ExecutorService {
    private final ExecutorService delegate;

    public MdcPropagatingExecutorService(ExecutorService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void execute(@NotNull Runnable command) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        delegate.execute(new MdcPropagatingRunnable(command, contextMap));
    }

    @Override
    @NotNull
    public <T> Future<T> submit(@NotNull Callable<T> task) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return delegate.submit(new MdcPropagatingCallable<>(task, contextMap));
    }

    @Override
    @NotNull
    public Future<?> submit(@NotNull Runnable task) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return delegate.submit(new MdcPropagatingRunnable(task, contextMap));
    }

    @Override
    @NotNull
    public <T> Future<T> submit(@NotNull Runnable task, T result) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return delegate.submit(new MdcPropagatingRunnable(task, contextMap), result);
    }

    @Override
    public void shutdown() {
        delegate.shutdown();
    }

    @Override
    @NotNull
    public List<Runnable> shutdownNow() {
        return delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return delegate.awaitTermination(timeout, unit);
    }

    @Override
    @NotNull
    public <T> List<Future<T>> invokeAll(@NotNull Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegate.invokeAll(tasks);
    }

    @Override
    @NotNull
    public <T> List<Future<T>> invokeAll(@NotNull Collection<? extends Callable<T>> tasks, long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return delegate.invokeAll(tasks, timeout, unit);
    }

    @Override
    @NotNull
    public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return delegate.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(@NotNull Collection<? extends Callable<T>> tasks, long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.invokeAny(tasks, timeout, unit);
    }
}
