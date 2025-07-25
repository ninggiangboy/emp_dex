package dev.ngb.empdex.infrastructure.event;

import dev.ngb.empdex.shared.core.event.Event;
import dev.ngb.empdex.shared.core.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisherImpl implements EventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(Event event) {
        eventPublisher.publishEvent(event);
    }
}
