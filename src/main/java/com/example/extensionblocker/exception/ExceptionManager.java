package com.example.extensionblocker.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(ExtensionException.class)
    public ResponseEntity<?> extensionExceptionHandler(ExtensionException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        ErrorCode errorCode = ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION;

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode, e.getMessage()));
    }

}
