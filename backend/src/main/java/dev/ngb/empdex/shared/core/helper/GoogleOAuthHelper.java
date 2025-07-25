package dev.ngb.empdex.shared.core.helper;

import java.util.Optional;

public interface GoogleOAuthHelper {
    Optional<String> authenticateAndGetUserEmail(String authorizationCode);
}