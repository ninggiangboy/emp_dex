package dev.ngb.empdex.user.internal.domain.error;

import dev.ngb.empdex.shared.core.domain.BusinessErrorCode;
import dev.ngb.empdex.shared.core.rest.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthErrorCode implements BusinessErrorCode {
    GOOGLE_OAUTH_ERROR("Google OAuth authentication failed", HttpStatus.UNAUTHORIZED),
    INVALID_VERIFICATION_CODE("Invalid verification code", HttpStatus.UNAUTHORIZED),
    SPAM_VERIFICATION_REQUIRED("Spam verification required", HttpStatus.TOO_MANY_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
