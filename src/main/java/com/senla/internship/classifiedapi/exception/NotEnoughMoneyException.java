package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that a user profile has not enough money on his balance
 * to proceed a payment.
 * @author Vlas Nagibin
 * @version 1.0
 */
public class NotEnoughMoneyException extends RuntimeException {

    /**
     * Constructs a {@code NotEnoughMoneyException} with the specified detail
     * message.
     * @param message the detail message
     */
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
