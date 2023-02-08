package com.senla.internship.classifiedapi.controller;

import com.senla.internship.classifiedapi.dto.request.MessageRequest;
import com.senla.internship.classifiedapi.exception.BadRequestException;
import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.service.high.level.MessageManagementService;
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
import java.util.List;

/**
 * The {@code MessageController} class is responsible for
 * managing chat related requests
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class MessageController {
    private final MessageManagementService messageManagementService;

    /**
     * Create a {@code CommentController} with a message management service.
     * @param messageManagementService the message management service
     */
    @Autowired
    public MessageController(MessageManagementService messageManagementService) {
        this.messageManagementService = messageManagementService;
    }

    /**
     * Return the response entity of sent message and the 200 HTTP status code.
     * @param userId the user ID whom a message is going to be sent
     * @param messageRequest the message data access object
     * @param bindingResult the binging result
     * @return the response entity to a client
     */
    @PostMapping("/users/{id}/profile/messages")
    public ResponseEntity<?> sendMessage(@PathVariable("id")
                                         @NotNull(message = "User ID should not be null.")
                                         @Min(value = 1, message = "User ID should be greater than or equal to 1.")
                                         Long userId,
                                         @Valid @RequestBody MessageRequest messageRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ErrorMessageGenerator.generateErrorMessage(bindingResult);
            log.error("Exception occurred with {}", messageRequest);
            throw new BadRequestException(message);
        }

        log.debug("Getting sent message");
        Message message = messageManagementService.sendMessage(userId, messageRequest);

        log.info("Message has been sent to a user");
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    /**
     * Return the response entity of all messages and the 200 HTTP status code.
     * @return the response entity to a client
     */
    @GetMapping("/profile/messages")
    public ResponseEntity<?> messages() {
        log.debug("Getting a list of messages");
        List<Message> messages = messageManagementService.receiveAllMessages();
        log.info("Messages have been returned to a user");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    /**
     * Return the response entity of a particular message and the 200 HTTP status code.
     * @param messageId the message id to find
     * @return the response entity to a client
     */
    @GetMapping("/profile/messages/{id}")
    public ResponseEntity<Message> message(@PathVariable("id")
                                         @NotNull(message = "Message ID should not be null.")
                                         @Min(value = 1, message = "Message ID should be greater tan or equal to 1.")
                                         Long messageId) {

        log.debug("Getting a particular message");
        Message message = messageManagementService.receiveMessage(messageId);
        log.info("A message has been returned to a user");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
