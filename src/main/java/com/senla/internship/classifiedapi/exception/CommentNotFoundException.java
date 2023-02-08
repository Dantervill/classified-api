package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that a comment is not found during its searching.
 * @author Vlas Nagibin
 * @version 1.0
 */
public class CommentNotFoundException extends RuntimeException {

    /**
     * Constructs a {@code CommentNotFoundException} with the specified detail
     * message.
     * @param message the detail message
     */
    public CommentNotFoundException(String message) {
        super(message);
    }
}
