package com.senla.internship.classifiedapi.repository;

import com.senla.internship.classifiedapi.model.account.Message;
import com.senla.internship.classifiedapi.model.account.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The {@code MessageRepository} class is the specific extension of {@link JpaRepository}.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds a message by id and recipient profile.
     * @param id the id. Must not be null.
     * @param recipient the recipient profile. Must not be null.
     * @return the optional advertisement
     */
    Optional<Message> findByIdAndRecipient(Long id, Profile recipient);

    /**
     * Finds all messages by recipient profile.
     * @param recipient the recipient profile. Must not be null.
     * @return the list of messages
     */
    List<Message> findAllByRecipient(Profile recipient);
}
