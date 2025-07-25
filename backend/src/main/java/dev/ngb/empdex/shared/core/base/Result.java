package dev.ngb.empdex.shared.core.base;

import dev.ngb.empdex.shared.core.domain.BusinessErrorCode;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Result<T> {
    static <T> Result<T> success(T value) {
        return new Success<>(value);
    }

    static Result<Void> success() {
        return new Success<>(null);
    }

    static <T> Result<T> failure(BusinessError error) {
        return new Failure<>(error);
    }

    static <T> Result<T> failure(BusinessErrorCode errorCode) {
        return new Failure<>(errorCode.getError());
    }

    boolean isSuccess();

    boolean isFailure();

    T get();

    BusinessError getError();

    default <U> Result<U> asFailure() {
        if (isFailure()) {
            return Result.failure(getError());
        }
        throw new IllegalStateException("Cannot convert success result to failure");
    }

    default <U> Result<U> map(Function<T, U> mapper) {
        if (isSuccess()) {
            return Result.success(mapper.apply(get()));
        } else {
            return Result.failure(getError());
        }
    }

    default <U> Result<U> flatMap(Function<T, Result<U>> mapper) {
        if (isSuccess()) {
            return mapper.apply(get());
        } else {
            return Result.failure(getError());
        }
    }

    default Result<T> onSuccess(Consumer<T> action) {
        if (isSuccess()) {
            action.accept(get());
        }
        return this;
    }

    default Result<T> onFailure(Consumer<BusinessError> action) {
        if (isFailure()) {
            action.accept(getError());
        }
        return this;
    }

    default Optional<T> toOptional() {
        return isSuccess() ? Optional.of(get()) : Optional.empty();
    }

    final class Success<T> implements Result<T> {
        private final T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public BusinessError getError() {
            throw new UnsupportedOperationException("No error in Success");
        }
    }

    final class Failure<T> implements Result<T> {
        private final BusinessError error;

        private Failure(BusinessError error) {
            this.error = error;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public T get() {
            throw new UnsupportedOperationException("No value in Failure");
        }

        @Override
        public BusinessError getError() {
            return error;
        }
    }
}