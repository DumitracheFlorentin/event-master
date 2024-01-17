package com.example.demo.advice;

import com.example.demo.exception.ErrorResponse;
import com.example.demo.exception.UserCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ErrorResponse> handleUserCreationException(UserCreationException ex) {
        HttpStatus status = determineHttpStatus(ex);

        ErrorResponse errorResponse = new ErrorResponse(status.value(), ex.getMessage());

        return ResponseEntity.status(status).body(errorResponse);
    }
    private HttpStatus determineHttpStatus(UserCreationException ex) {
        if (ex instanceof UserCreationException) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}

