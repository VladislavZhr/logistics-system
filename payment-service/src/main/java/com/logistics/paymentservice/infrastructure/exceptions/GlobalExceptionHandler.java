package com.logistics.paymentservice.infrastructure.exceptions;

import com.logistics.paymentservice.infrastructure.exceptions.error.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(NoFoundInvoiceIdException.class)
    public ResponseEntity<ErrorResponse> handleNoFoundInvoiceIdException(NoFoundInvoiceIdException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(TransactionIsAlreadyProcessed.class)
    public ResponseEntity<ErrorResponse> handleTransactionIsAlreadyProcessed(TransactionIsAlreadyProcessed ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoFoundUserIdException.class)
    public ResponseEntity<ErrorResponse> handleNoFoundUserIdException(NoFoundUserIdException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(StripePaymentException.class)
    public ResponseEntity<ErrorResponse> handleStripePaymentException(StripePaymentException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
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
