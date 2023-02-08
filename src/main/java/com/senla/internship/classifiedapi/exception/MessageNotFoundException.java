package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that a message is not found during its searching.
 * @author Vlas Nagibin
 * @version 1.0
 */
public class MessageNotFoundException extends RuntimeException {

    /**
     * Constructs a {@code MessageNotFoundException} with the specified detail
     * message.
     * @param message the detail message
     */
    public MessageNotFoundException(String message) {
        super(message);
    }
}
