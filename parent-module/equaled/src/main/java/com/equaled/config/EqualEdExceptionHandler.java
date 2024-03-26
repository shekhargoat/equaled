package com.equaled.config;

import com.equaled.customexception.ErrorResponse;
import com.equaled.customexception.InvalidSidException;
import com.equaled.eserve.common.exception.ApplicationException;
import com.equaled.eserve.common.exception.RecordNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.equaled.eserve.common.exception.InvalidTokenException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EqualEdExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            InvalidTokenException.class,
            com.equaled.value.EqualEdException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            IllegalArgumentException.class,
            IllegalStateException.class,
            ApplicationException.class,
            RecordNotFoundException.class,
            InvalidSidException.class
    })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        if (ex instanceof ApplicationException) {
            ErrorResponse errors = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(((ApplicationException) ex).devMessage)
                    .error(ex.getLocalizedMessage()).build();
            return new ResponseEntity<Object>(errors, new HttpHeaders(), errors.getStatus());
        } else if (ex instanceof RecordNotFoundException) {
            ErrorResponse errors = ErrorResponse.builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message(((RecordNotFoundException) ex).devMessage)
                    .error(ex.getLocalizedMessage()).build();
            return new ResponseEntity<Object>(errors, new HttpHeaders(), errors.getStatus());
        } else if (ex instanceof InvalidSidException) {
            ErrorResponse errors = ErrorResponse.builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message(((InvalidSidException) ex).devMessage)
                    .error(ex.getLocalizedMessage()).build();
            return new ResponseEntity<>(errors, new HttpHeaders(), errors.getStatus());
        } else {
            ErrorResponse errors = ErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ex.getMessage())
                    .error(ex.getLocalizedMessage()).build();
            return new ResponseEntity<>(errors, new HttpHeaders(), errors.getStatus());
        }
    }
}
