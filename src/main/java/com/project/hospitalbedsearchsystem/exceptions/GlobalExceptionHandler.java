package com.project.hospitalbedsearchsystem.exceptions;

import com.project.hospitalbedsearchsystem.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
