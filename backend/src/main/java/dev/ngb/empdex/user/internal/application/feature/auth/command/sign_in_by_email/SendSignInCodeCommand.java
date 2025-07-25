package dev.ngb.empdex.user.internal.application.feature.auth.command.sign_in_by_email;

import dev.ngb.empdex.shared.core.mediator.Request;

public record SendSignInCodeCommand(String email) implements Request<Void> {
}
