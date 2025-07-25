package dev.ngb.empdex.user.internal.domain.error;

import dev.ngb.empdex.shared.core.domain.BusinessErrorCode;
import dev.ngb.empdex.shared.core.rest.HttpStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements BusinessErrorCode {
    USERNAME_OR_EMAIL_EXISTS("Username or email already exists", HttpStatus.CONFLICT),
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    USER_INVALID_INPUT("Invalid user input provided", HttpStatus.BAD_REQUEST),
    USER_ALREADY_SUSPENDED("User is already suspended", HttpStatus.BAD_REQUEST),
    USER_NOT_SUSPENDED("User is not suspended", HttpStatus.BAD_REQUEST),
    IMPORT_JOB_NOT_FOUND("Import job not found", HttpStatus.NOT_FOUND),
    IMPORT_JOB_NOT_VALIDATED("Import job is not validated", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;
}
