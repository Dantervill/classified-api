package com.senla.internship.classifiedapi.exception.handler;

import com.senla.internship.classifiedapi.dto.response.ClassicResponse;
import com.senla.internship.classifiedapi.exception.*;
import com.senla.internship.classifiedapi.utils.generator.ErrorMessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

/**
 * The {@code GlobalResponseEntityExceptionHandler} class is the child-class of
 * {@link ResponseEntityExceptionHandler}. It provides exception handling of the
 * application during its execution.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the {@link RegistrationFailedException} and returns the response entity
     * of the 409 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<ClassicResponse> handleException(RegistrationFailedException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONTINUE.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles the {@link NotEnoughMoneyException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ClassicResponse> handleException(NotEnoughMoneyException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link BadRequestException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleException(BadRequestException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link AuthenticationException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleException(AuthenticationException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles the {@link BadCredentialsException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleException(BadCredentialsException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles the {@link ConstraintViolationException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleException(ConstraintViolationException e, HttpServletRequest request) {
        String message = ErrorMessageGenerator.generateErrorMessage(e.getConstraintViolations());
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the {@link RoleNotFoundException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(RoleNotFoundException.class)
    private ResponseEntity<?> handleException(RoleNotFoundException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link UserNotFoundException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleException(UserNotFoundException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link AdvertisementNotFoundException} and returns the response entity
     * of the 404 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(AdvertisementNotFoundException.class)
    public ResponseEntity<?> handleException(AdvertisementNotFoundException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link MessageNotFoundException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<?> handleException(MessageNotFoundException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the {@link DataNotFoundException} and returns the response entity
     * of the 200 HTTP status code and a response object.
     * @param e the exception
     * @param request the HTTP request
     * @return the response entity
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleException(DataNotFoundException e, HttpServletRequest request) {
        ClassicResponse classicResponse = new ClassicResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
        log.error("Exception occurred. Message: {}", e.getMessage());
        return new ResponseEntity<>(classicResponse, HttpStatus.NOT_FOUND);
    }
}
