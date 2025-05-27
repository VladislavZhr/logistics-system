package com.logistics.routingservice.infrastructure.execeptions;

import com.logistics.routingservice.infrastructure.execeptions.error.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(OrderAlreadyDeliveredException.class)
    public ResponseEntity<ErrorResponse> handleOrderAlreadyDeliveredException(OrderAlreadyDeliveredException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(OrderStatusAlreadySetException.class)
    public ResponseEntity<ErrorResponse> handleOrderStatusAlreadySetException(OrderStatusAlreadySetException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoAvailableWarehouseException.class)
    public ResponseEntity<ErrorResponse> handleNoAvailableWarehouseException(NoAvailableWarehouseException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoExistingWarehouseException.class)
    public ResponseEntity<ErrorResponse> handleNoExistingWarehouseException(NoExistingWarehouseException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleNoCredentialsException(NoCredentialsException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }
}
