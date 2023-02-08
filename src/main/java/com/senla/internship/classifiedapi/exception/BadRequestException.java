package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that a request contains invalid data.
 * @author Vlas Nagibin
 * @version 1.0
 */
public class BadRequestException extends RuntimeException {

    /**
     * Constructs a {@code BadRequestException} with the specified detail
     * message.
     * @param message the detail message
     */
    public BadRequestException(String message) {
        super(message);
    }
}
