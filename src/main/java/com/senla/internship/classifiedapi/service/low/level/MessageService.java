package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.DataNotFoundException;
import com.senla.internship.classifiedapi.exception.MessageNotFoundException;
import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom implementation of the {@link IMessageService} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class MessageService implements IMessageService {
    private static final String DATA_NOT_FOUND_MESSAGE = "Data not found";
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message save(Message message) {
        log.debug("Message has been saved");
        return messageRepository.save(message);
    }

    @Override
    public Message findByIdAndRecipient(Long id, Profile recipient) throws MessageNotFoundException {
        String message = String.format("Message with ID %d and Recipient %s not found", id, recipient);
        log.info("Message has been found by id and profile");
        return messageRepository.findByIdAndRecipient(id, recipient)
                .orElseThrow(() -> new MessageNotFoundException(message));
    }

    @Override
    public List<Message> findAllByRecipient(Profile recipient) throws DataNotFoundException {
        log.debug("Delegating messages searching to the {}", messageRepository.getClass().getName());
        List<Message> messages = messageRepository.findAllByRecipient(recipient);

        log.debug("Checking if list of messages is empty");
        if (messages.isEmpty()) {
            throw new DataNotFoundException(DATA_NOT_FOUND_MESSAGE);
        }

        log.info("Message has been returned");
        return messages;
    }
}
