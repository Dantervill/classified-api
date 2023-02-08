package com.senla.internship.classifiedapi.repository;

import com.senla.internship.classifiedapi.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The {@code UserRepository} class is the specific extension of {@link JpaRepository}.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by email.
     * @param email the email. Must not be null.
     * @return the user
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks if user exists by email.
     * @param email the email. Must not be null.
     * @return the boolean value
     */
    boolean existsByEmail(String email);

    /**
     * Checks if user exists by id.
     * @param id the id. Must not be null.
     * @return the boolean value
     */
    boolean existsById(Long id);
}
