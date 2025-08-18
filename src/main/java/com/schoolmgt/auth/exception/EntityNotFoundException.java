package com.schoolmgt.auth.exception;

/**
 * Custom exception for when an entity is not found.
 * This will be mapped to HTTP 404 status by the GlobalExceptionHandler.
 */
public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String entityType, Long id) {
        super(String.format("%s not found with id: %d", entityType, id));
    }
    
    public EntityNotFoundException(String entityType, String field, String value) {
        super(String.format("%s not found with %s: %s", entityType, field, value));
    }
}