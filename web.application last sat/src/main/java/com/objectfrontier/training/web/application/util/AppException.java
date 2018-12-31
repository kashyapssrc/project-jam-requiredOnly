package com.objectfrontier.training.web.application.util;

import java.util.ArrayList;
import java.util.List;

public class AppException extends RuntimeException{

    private static final long serialVersionUID = -1711827770663962725L;
    List<ErrorCodes> errorCodes = new ArrayList<>();
    ErrorCodes error;
    Throwable cause;

    public AppException(List<ErrorCodes> errorCodes) {
        super();
        this.errorCodes = errorCodes;
    }

    public AppException(ErrorCodes errorCode, Throwable cause) {
        super(errorCode.getErrorMessage(), cause);
        if (errorCodes != null && errorCodes.size() != 0) {
            errorCodes = new ArrayList<>();
            errorCodes.add(errorCode);
            this.cause = cause;
        }
    }

    public AppException(Throwable cause) {
        this(ErrorCodes.UNKNOWN_ERROR, cause);
    }

    public AppException(List<ErrorCodes> errorCodes, String message, Exception e) {
        super();
        this.errorCodes = errorCodes;
    }

    public AppException(ErrorCodes error) {
        super();
        this.error = error;
    }

    public List<ErrorCodes> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<ErrorCodes> errorCodes) {
        this.errorCodes = errorCodes;
    }

}
