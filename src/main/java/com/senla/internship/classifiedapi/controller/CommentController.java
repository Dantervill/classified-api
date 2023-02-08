package com.senla.internship.classifiedapi.controller;

import com.senla.internship.classifiedapi.dto.request.CommentRequest;
import com.senla.internship.classifiedapi.exception.BadRequestException;
import com.senla.internship.classifiedapi.model.advertisement.Comment;
import com.senla.internship.classifiedapi.service.high.level.CommentManagementService;
import com.senla.internship.classifiedapi.utils.generator.ErrorMessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The {@code CommentController} class is responsible for
 * managing comment related requests.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/v1")
public class CommentController {
    private final CommentManagementService commentManagementService;

    /**
     * Create a {@code CommentController} with a comment management service.
     * @param commentManagementService the comment management service
     */
    @Autowired
    public CommentController(CommentManagementService commentManagementService) {
        this.commentManagementService = commentManagementService;
    }

    /**
     * Return the response entity of a comment and the 200 HTTP status code.
     * @param userId the user ID whose advertisement is going to be commented
     * @param advertisementId the advertisement ID which is going to be commented
     * @param commentRequest the comment data access object
     * @param bindingResult the binging result
     * @return the response entity to a client
     * @throws BadRequestException the bad request exception in case of failed validation
     */
    @PostMapping("/users/{id}/profile/advertisements/active-advertisements/{advertisementId}/comments")
    public ResponseEntity<?> postComment(@PathVariable("id")
                                         @NotNull(message = "User ID should not be null.")
                                         @Min(value = 1, message = "User ID should be greater than or equal to 1.")
                                         Long userId,
                                         @PathVariable("advertisementId")
                                         @NotNull(message = "Advertisement ID should not be null.")
                                         @Min(value = 1, message = "Advertisement ID should be greater than or equal to 1.")
                                         Long advertisementId,
                                         @Valid @RequestBody CommentRequest commentRequest, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            String message = ErrorMessageGenerator.generateErrorMessage(bindingResult);
            log.error("Exception occurred with {}", commentRequest);
            throw new BadRequestException(message);
        }

        log.debug("Getting a posted comment");
        Comment comment = commentManagementService.postComment(userId, advertisementId, commentRequest);

        log.info("A user comment has been posted to an advertisement");
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
