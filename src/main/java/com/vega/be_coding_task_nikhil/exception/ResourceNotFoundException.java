package com.vega.be_coding_task_nikhil.exception;

/**
 * Custom exception class for resource not found scenarios.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

