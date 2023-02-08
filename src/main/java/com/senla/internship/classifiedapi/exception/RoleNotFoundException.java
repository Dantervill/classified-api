package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that a role is not found during its searching.
 * @author Vlas Nagibin
 * @version 1.0
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Constructs a {@code RoleNotFoundException} with the specified detail
     * message.
     * @param message the detail message
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}
