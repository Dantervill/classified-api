package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.account.Profile;

/**
 * Core interface which processes profile-specific-data
 * @author Vlas Nagibin
 * @version 1.0
 */
public interface IProfileService {

    /**
     * Saves a user profile
     * @param profile the user profile
     * @return the saved user profile
     */
    Profile save(Profile profile);

    /**
     * Checks if a user profile exists by phone
     * @param phone the phone
     * @return the boolean value
     */
    boolean existsByPhone(String phone);
}
