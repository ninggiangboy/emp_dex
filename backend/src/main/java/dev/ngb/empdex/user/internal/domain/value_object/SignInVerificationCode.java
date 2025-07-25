package dev.ngb.empdex.user.internal.domain.value_object;

import dev.ngb.empdex.shared.core.domain.ValueObject;
import dev.ngb.empdex.shared.core.util.StringUtils;

import java.time.Duration;

public record SignInVerificationCode(String value, String userId, long expirationInMillis) implements ValueObject {
    private final static int CODE_LENGTH = 6;

    public SignInVerificationCode {
        if (value == null || value.isBlank() || value.trim().length() != CODE_LENGTH) {
            throw new IllegalArgumentException("Verification code cannot be null or blank");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or blank");
        }
        if (expirationInMillis <= 0) {
            throw new IllegalArgumentException("Expiration time must be greater than zero");
        }
    }

    public static SignInVerificationCode create(String userId, Duration ttl) {
        String codeValue = StringUtils.generateSecureRandomDigits(CODE_LENGTH);
        long expiration = ttl.toMillis();
        return new SignInVerificationCode(codeValue, userId, expiration);
    }

    public long expirationInMinutes() {
        return Duration.ofMillis(expirationInMillis).toMinutes();
    }
}
