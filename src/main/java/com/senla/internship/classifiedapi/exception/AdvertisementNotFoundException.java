package com.senla.internship.classifiedapi.exception;

/**
 * Thrown to indicate that an advertisement is not found during its searching.
 * @author Vlas Nagbin
 * @version 1.0
 */
public class AdvertisementNotFoundException extends RuntimeException {

    /**
     * Constructs an {@code AdvertisementNotFoundException} with the specified detail
     * message.
     * @param message the detail message
     */
    public AdvertisementNotFoundException(String message) {
        super(message);
    }
}
