package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.model.account.Profile;

import java.util.List;

/**
 * Core interface which processes message-specific-data.
 * @author Vlas Nagibin
 * @version 1.0
 */
public interface IMessageService {

    /**
     * Saves a message.
     * @param message the message
     * @return the saved message
     */
    Message save(Message message);

    /**
     * Finds a message by id and user recipient profile.
     * @param id the id
     * @param profile the user recipient profile
     * @return the message
     */
    Message findByIdAndRecipient(Long id, Profile profile);

    /**
     * Finds messages by user recipient profile
     * @param recipient the user recipient profile
     * @return the list of messages
     */
    List<Message> findAllByRecipient(Profile recipient);
}
