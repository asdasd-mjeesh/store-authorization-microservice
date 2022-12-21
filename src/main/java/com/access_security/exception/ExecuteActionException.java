package com.access_security.exception;

import org.springframework.http.HttpStatus;

public class ExecuteActionException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ExecuteActionException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
