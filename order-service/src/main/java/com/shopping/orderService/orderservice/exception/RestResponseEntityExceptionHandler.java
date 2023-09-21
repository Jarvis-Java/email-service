package com.shopping.orderService.orderservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shopping.orderService.orderservice.response.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(OrderServiceCustomException exception) {
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(),
                HttpStatus.valueOf(exception.getStatus()));
    }
}