package com.access_security.api.exception_handler;

import com.access_security.exception.ExecuteActionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(value = { ExecuteActionException.class })
    public ResponseEntity<Object> entityNotFoundHandler(ExecuteActionException e) {
        var exceptionDetails = ExceptionResponseDetails.builder()
                .message(e.getMessage())
                .exception(e.getCause())
                .httpStatus(e.getHttpStatus())
                .time(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, e.getHttpStatus());
    }
}
