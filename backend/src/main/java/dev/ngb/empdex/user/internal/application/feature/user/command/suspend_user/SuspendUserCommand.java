package dev.ngb.empdex.user.internal.application.feature.user.command.suspend_user;

import dev.ngb.empdex.shared.core.mediator.Request;

import java.util.UUID;

public record SuspendUserCommand(UUID userId) implements Request<Void> {
}
