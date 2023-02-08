package com.senla.internship.classifiedapi.repository;

import com.senla.internship.classifiedapi.model.account.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code ProfileRepository} class is the specific extension of {@link JpaRepository}.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Checks if user profile exists by phone.
     * @param phone the phone. Must not be null.
     * @return the boolean value
     */
    boolean existsByPhone(String phone);

    /**
     * Checks if user profile exists by id.
     * @param id the id. Must not be null.
     * @return the boolean value
     */
    boolean existsById(Long id);
}
