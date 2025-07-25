package dev.ngb.empdex.shared.core.event;

public interface EventHandler<T extends Event> {
    void handle(T event);
}