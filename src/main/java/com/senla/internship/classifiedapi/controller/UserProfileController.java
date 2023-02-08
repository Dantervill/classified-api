package com.senla.internship.classifiedapi.controller;

import com.senla.internship.classifiedapi.dto.request.ProfileRequest;
import com.senla.internship.classifiedapi.exception.BadRequestException;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.service.high.level.ProfileManagementService;
import com.senla.internship.classifiedapi.utils.generator.ErrorMessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The {@code UserProfileController} class is responsible for managing users' profiles.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1", consumes = "application/json", produces = "application/json")
public class UserProfileController {
    private final ProfileManagementService profileManagementService;

    /**
     * Create a {@code UserProfileController} with a profile management service.
     * @param profileManagementService the profile management service
     */
    @Autowired
    public UserProfileController(ProfileManagementService profileManagementService) {
        this.profileManagementService = profileManagementService;
    }

    /**
     * Return the response entity of edited profile and the 200 HTTP status code.
     * @param profileRequest the profile data access object
     * @param bindingResult the binding result
     * @return the response entity to a client
     */
    @PutMapping(value = "/profile/management/editing")
    public ResponseEntity<?> editing(@Valid @RequestBody ProfileRequest profileRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ErrorMessageGenerator.generateErrorMessage(bindingResult);
            log.error("{} occurred with {}", BadRequestException.class.getName(), profileRequest);
            throw new BadRequestException(message);
        }

        log.debug("Getting an edited profile");
        Profile profile = profileManagementService.performProfileEditing(profileRequest);

        log.info("Edited profile has been returned to a user");
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
