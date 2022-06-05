package com.example.mmmcourier.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CourierNotFoundExp.class)
    public ResponseEntity<Object> handleValidationException(CourierNotFoundExp ex) {
        final ApiError apiError;
        apiError = createApiError(HttpStatus.NOT_FOUND, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    private ApiError createApiError(HttpStatus httpStatus, List<String> errors) {
        ApiError apiError = new ApiError();
        apiError.setHttpStatus(httpStatus);
        apiError.setResultCode(httpStatus.getReasonPhrase());
        apiError.setStatus(httpStatus.value());
        apiError.setMessages(errors);
        return apiError;
    }
}

