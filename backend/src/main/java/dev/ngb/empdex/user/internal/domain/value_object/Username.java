package dev.ngb.empdex.user.internal.domain.value_object;

import dev.ngb.empdex.shared.core.domain.ValueObject;

public record Username(String value) implements ValueObject {
    public Username {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Username cannot be blank");
        if (value.length() < 3)
            throw new IllegalArgumentException("Username must be at least 3 characters");
        if (value.length() > 50)
            throw new IllegalArgumentException("Username cannot exceed 50 characters");
        if (!value.matches("^[a-zA-Z0-9._-]+$"))
            throw new IllegalArgumentException("Invalid characters in username");
    }
}
