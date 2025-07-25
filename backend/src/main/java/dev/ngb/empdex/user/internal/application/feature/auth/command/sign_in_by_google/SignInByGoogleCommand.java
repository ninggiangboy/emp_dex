package dev.ngb.empdex.user.internal.application.feature.auth.command.sign_in_by_google;

import dev.ngb.empdex.shared.core.mediator.Request;

public record SignInByGoogleCommand(String authorizationCode, String ipAddress, String userAgent) implements Request<String> {
}