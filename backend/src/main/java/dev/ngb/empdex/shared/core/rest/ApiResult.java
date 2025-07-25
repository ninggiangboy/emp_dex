package dev.ngb.empdex.shared.core.rest;

public record ApiResult<T>(T data, String message, String errorCode, boolean isSuccess) {
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data, "Operation successful", null, true);
    }

    public static <T> ApiResult<T> success(T data, String message) {
        return new ApiResult<>(data, message, null, true);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(null, "Operation successful", null, true);
    }

    public static <T> ApiResult<T> error(String errorCode, String message) {
        return new ApiResult<>(null, message, errorCode, false);
    }

    public static <T> ApiResult<T> error(String errorCode) {
        return new ApiResult<>(null, "An error occurred", errorCode, false);
    }

    public static <T> ApiResult<T> error() {
        return new ApiResult<>(null, "An error occurred", "UNKNOWN_ERROR", false);
    }
}
