package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that a user is not found during its searching.
 * @author Vlas Nagibin
 * @version 1.0
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructs a {@code RoleNotFoundException} with the specified detail
     * message.
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
