package com.schoolmgt.auth.exception;

/**
 * Custom exception for business logic errors.
 * This will be mapped to HTTP 500 status by the GlobalExceptionHandler.
 */
public class BusinessLogicException extends Exception {
    
    public BusinessLogicException(String message) {
        super(message);
    }
    
    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
