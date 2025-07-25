package dev.ngb.empdex.shared.core.domain;

import dev.ngb.empdex.shared.core.event.Event;
import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class DomainEvent implements Event {
    private final Instant occurredOn;
    private final String type;

    public DomainEvent() {
        this.occurredOn = Instant.now();
        this.type = this.getClass().getSimpleName();
    }
}