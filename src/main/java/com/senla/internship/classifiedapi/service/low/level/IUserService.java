package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.account.User;

import java.util.List;

/**
 * Core interface which processes user specific-data
 * @author Vlas Nagibin
 * @version 1.0
 */
public interface IUserService {

    /**
     * Finds a user by email.
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Finds a user by id.
     * @param id the id
     * @return the user
     */
    User findById(Long id);

    /**
     * Checks if a user exists by email
     * @param email the email
     * @return the boolean value
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user by id.
     * @param id the id
     * @return the boolean value
     */
    boolean existsById(Long id);

    /**
     * Saves a user.
     * @param user the user
     * @return the saved user
     */
    User save(User user);

    /**
     * Finds all advertisements.
     * @return the list of advertisements
     */
    List<User> findAll();
}
