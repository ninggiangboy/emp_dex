package dev.ngb.empdex.shared.core.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot<T> extends Entity<T> {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public List<DomainEvent> getDomainEvents() {
        return List.copyOf(domainEvents);
    }

    public void registerDomainEvent(DomainEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Domain event cannot be null");
        }
        domainEvents.add(event);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
