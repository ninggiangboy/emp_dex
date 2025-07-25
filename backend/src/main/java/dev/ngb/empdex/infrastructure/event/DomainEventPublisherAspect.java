package dev.ngb.empdex.infrastructure.event;

import dev.ngb.empdex.shared.core.domain.AggregateRoot;
import dev.ngb.empdex.shared.core.domain.DomainEvent;
import dev.ngb.empdex.shared.core.event.EventPublisher;
import dev.ngb.empdex.shared.core.helper.TransactionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DomainEventPublisherAspect {
    private final EventPublisher eventPublisher;
    private final TransactionHelper transactionHelper;

    @Around("execution(* dev.ngb.empdex..repository.*Repository.save(..))")
    public Object publishDomainEventsFromRepositorySave(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        if (args.length == 0 || args[0] == null || !(args[0] instanceof AggregateRoot<?> aggregate)) {
            return joinPoint.proceed();
        }

        List<DomainEvent> domainEventsToPublish = aggregate.getDomainEvents();

        Object result = joinPoint.proceed();

        if (domainEventsToPublish != null && !domainEventsToPublish.isEmpty()) {
            publishDomainEventsAfterCommit(domainEventsToPublish, joinPoint);
        }

        return result;
    }

    private void publishDomainEventsAfterCommit(List<DomainEvent> domainEvents, ProceedingJoinPoint joinPoint) {
        // Publish events after transaction commit to ensure data consistency
        transactionHelper.runAfterCommit(() -> {
            domainEvents.forEach(event -> {
                try {
                    log.debug("Publishing domain event: {}", event.getType());
                    eventPublisher.publish(event);
                } catch (Exception e) {
                    log.error("Error publishing domain event: {}", event.getType(), e);
                }
            });
        });
    }
}
