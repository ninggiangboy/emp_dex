package dev.ngb.empdex.user.internal.domain.event;

import dev.ngb.empdex.shared.core.domain.DomainEvent;
import dev.ngb.empdex.user.internal.domain.model.LoginHistory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TooManyFailedLoginsEvent extends DomainEvent {
    private final LoginHistory lastRecentLoginHistory;
}
