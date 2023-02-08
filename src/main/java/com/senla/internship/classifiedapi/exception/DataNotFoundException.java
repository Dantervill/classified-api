package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that data is not found during its searching.
 * @author Vlas Nagibin
 * @version 1.0
 */
public class DataNotFoundException extends RuntimeException {

    /**
     * Constructs a {@code DataNotFoundException} with the specified detail
     * message.
     * @param message the detail message
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
