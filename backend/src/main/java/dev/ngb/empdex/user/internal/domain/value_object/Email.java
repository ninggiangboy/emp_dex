package dev.ngb.empdex.user.internal.domain.value_object;

import dev.ngb.empdex.shared.core.domain.ValueObject;

public record Email(String value) implements ValueObject {
    public Email {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Email cannot be blank");
        if (value.length() > 255)
            throw new IllegalArgumentException("Email cannot exceed 255 characters");
        if (!isValidEmail(value))
            throw new IllegalArgumentException("Email format is invalid");
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
