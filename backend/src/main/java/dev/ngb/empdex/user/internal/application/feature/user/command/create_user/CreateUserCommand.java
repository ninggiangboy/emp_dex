package dev.ngb.empdex.user.internal.application.feature.user.command.create_user;

import dev.ngb.empdex.shared.core.mediator.Request;

import java.util.UUID;

public record CreateUserCommand(String username, String email) implements Request<UUID> {
}
