package com.senla.internship.classifiedapi.utils.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * The {@code ErrorMessageGenerator} is the utility class which is responsible
 * for managing collection of error messages.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
public class ErrorMessageGenerator {

    /**
     * Returns the detailed message about object's fields that failed the validation.
     * @param bindingResult the binding result
     * @return the detailed message
     */
    public static String generateErrorMessage(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        log.debug("Collecting object error messages");
        bindingResult.getAllErrors().forEach(objectError -> builder.append(objectError.getDefaultMessage()).append(" "));
        log.info("Message {} has been generated", builder.toString().trim());
        return builder.toString().trim();
    }

    /**
     * Returns the detailed message about constraint violations met in the request.
     * @param violations the violations
     * @return the detailed message
     */
    public static String generateErrorMessage(Set<ConstraintViolation<?>> violations) {
        StringBuilder builder = new StringBuilder();
        log.debug("Collecting constraint violation messages");
        violations.forEach(constraintViolation -> builder.append(constraintViolation.getMessage()).append(" "));
        log.info("Message {} has been generated", builder.toString().trim());
        return builder.toString().trim();
    }
}
