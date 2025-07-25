package dev.ngb.empdex.shared.core.base;

public record BusinessError(String code, String message, int httpStatus) {
    public BusinessError {
        if (httpStatus < 400 || httpStatus >= 500) {
            throw new IllegalArgumentException("HTTP status must be a 4xx code");
        }
    }
}
