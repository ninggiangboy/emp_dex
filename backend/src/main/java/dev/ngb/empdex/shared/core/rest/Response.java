package dev.ngb.empdex.shared.core.rest;

import dev.ngb.empdex.shared.core.base.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response<T> extends ResponseEntity<ApiResult<T>> {
    private Response(ApiResult<T> body, int statusCode) {
        super(body, HttpStatus.valueOf(statusCode));
    }

    public static <T> Response<T> create(Result<T> result, HttpStatus status) {
        if (result.isSuccess()) {
            return new Response<>(ApiResult.success(result.get()), status.value());
        } else {
            var error = result.getError();
            return new Response<>(ApiResult.error(error.message(), error.code()), error.httpStatus());
        }
    }

    public static <T> Response<T> create(Result<T> result) {
        return create(result, HttpStatus.OK);
    }
}
