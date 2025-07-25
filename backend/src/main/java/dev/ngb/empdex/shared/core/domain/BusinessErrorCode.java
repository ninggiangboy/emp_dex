package dev.ngb.empdex.shared.core.domain;

import dev.ngb.empdex.shared.core.base.BusinessError;
import dev.ngb.empdex.shared.core.rest.HttpStatus;

public interface BusinessErrorCode {
    String getMessage();
    HttpStatus getHttpStatus();

    default BusinessError getError() {
        try {
            String errorCode = ((Enum<?>) this).name();
            return new BusinessError(errorCode, getMessage(), getHttpStatus().getCode());
        } catch (ClassCastException e) {
            return new BusinessError("UNKNOW_ERROR", getMessage(), 400);
        }
    }
}
