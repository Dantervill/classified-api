package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.MessageRequest;
import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.account.User;
import com.senla.internship.classifiedapi.security.authentication.IAuthenticationFacade;
import com.senla.internship.classifiedapi.service.low.level.IMessageService;
import com.senla.internship.classifiedapi.service.low.level.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Solves problems of business logic related to message-specific-requirements
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class MessageManagementService {
    private final IUserService userService;
    private final IMessageService messageService;
    private final IAuthenticationFacade authenticationFacade;

    /**
     * Constructs a {@code MessageManagementService} with a user service,
     * message service, and authentication facade.
     * @param userService the user service
     * @param messageService the message service
     * @param authenticationFacade the authentication facade
     */
    public MessageManagementService(IUserService userService,
                                    IMessageService messageService,
                                    IAuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.messageService = messageService;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * Receives messages.
     * @return the list of messages
     */
    public List<Message> receiveAllMessages() {
        log.debug("Receiving all messages");
        String email = authenticationFacade.getAuthentication().getName();
        User user = userService.findByEmail(email);
        Profile current = user.getProfile();
        List<Message> messages = messageService.findAllByRecipient(current);
        log.info("Received messages have been returned to the Controller Layer");
        return messages;
    }

    /**
     * Gets a message.
     * @param messageId the message id
     * @return the advertisement
     */
    public Message receiveMessage(Long messageId) {
        log.debug("Receiving a message");
        String email = authenticationFacade.getAuthentication().getName();
        User user = userService.findByEmail(email);
        Profile current = user.getProfile();
        Message message = messageService.findByIdAndRecipient(messageId, current);
        message.setRead(true);
        message = messageService.save(message);
        log.info("Received message has been returned to the Controller Layer");
        return message;
    }

    /**
     * Sends a message.
     * @param userId the user id
     * @param messageRequest the message data access object
     * @return sent message
     */
    public Message sendMessage(Long userId, MessageRequest messageRequest) {
        log.debug("Sending message");
        Profile sender = userService.findByEmail(authenticationFacade.getAuthentication().getName()).getProfile();
        Profile recipient = userService.findById(userId).getProfile();
        Message message = buildMessage(sender, recipient, messageRequest);
        message = messageService.save(message);
        log.info("Sent message has been returned to the Controller Layer");
        return message;
    }

    private Message buildMessage(Profile sender, Profile recipient, MessageRequest dto) {
        boolean defaultDelivered = true;
        boolean defaultRead = false;
        return Message.builder()
                .sender(sender)
                .recipient(recipient)
                .sentAt(LocalDateTime.now())
                .text(dto.getText())
                .delivered(defaultDelivered)
                .read(defaultRead)
                .build();
    }
}
