package dev.ngb.empdex.user.internal.application.feature.user.command.reactivate_user;

import dev.ngb.empdex.shared.core.mediator.Request;

import java.util.UUID;

public record ReactivateUserCommand(UUID userId) implements Request<Void> {
}
