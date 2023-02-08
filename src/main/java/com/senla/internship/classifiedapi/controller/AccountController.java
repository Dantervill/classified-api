package com.senla.internship.classifiedapi.controller;

import com.senla.internship.classifiedapi.dto.request.LoginRequest;
import com.senla.internship.classifiedapi.dto.request.RegistrationRequest;
import com.senla.internship.classifiedapi.dto.response.TokenResponse;
import com.senla.internship.classifiedapi.exception.BadRequestException;
import com.senla.internship.classifiedapi.service.high.level.AuthenticationService;
import com.senla.internship.classifiedapi.service.high.level.RegistrationService;
import com.senla.internship.classifiedapi.utils.generator.ErrorMessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The {@code AccountController} class is responsible for managing
 * a user authentication/registration requests.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1", consumes = "application/json", produces = "application/json")
public class AccountController {
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;

    /**
     * Create an {@code AccountController } with a registration service,
     * authenticationService, and a constraint validator.
     * @param registrationService the registration service
     * @param authenticationService the authentication service
     */
    @Autowired
    public AccountController(RegistrationService registrationService,
                             AuthenticationService authenticationService) {
        this.registrationService = registrationService;
        this.authenticationService = authenticationService;
    }

    /**
     * Return the response entity of a jwt token and the 200 HTTP status code.
     * @param registrationRequest the registration data access object
     * @param bindingResult the binding result.
     * @return the response entity to a client
     * @throws BadRequestException the bad request exception in case of failed validation
     */
    @ResponseBody
    @PostMapping("/accounts/signup")
    public ResponseEntity<TokenResponse> signUp(@Valid @RequestBody RegistrationRequest registrationRequest, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            String message = ErrorMessageGenerator.generateErrorMessage(bindingResult);
            log.error("Exception occurred with {}", registrationRequest);
            throw new BadRequestException(message);
        }

        log.debug("Receiving a token");
        TokenResponse tokenResponse = registrationService.performRegistration(registrationRequest);

        log.info("Registration succeed");
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    /**
     * Return the response entity of a jwt token and the 200 HTTP status code.
     * @param loginRequest the login data access object
     * @param bindingResult the binging result
     * @return the response entity to a client
     * @throws BadRequestException the bad request exception
     */
    @PostMapping("/accounts/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            String message = ErrorMessageGenerator.generateErrorMessage(bindingResult);
            log.error("Exception occurred with {}", loginRequest);
            throw new BadRequestException(message);
        }

        log.debug("Receiving a token");
        TokenResponse tokenResponse = authenticationService.performLogin(loginRequest);

        log.info("Login succeed");
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}
