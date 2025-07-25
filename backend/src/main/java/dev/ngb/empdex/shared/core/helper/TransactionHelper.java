package dev.ngb.empdex.shared.core.helper;

@FunctionalInterface
public interface TransactionHelper {
    void runAfterCommit(Runnable runnable);
}
