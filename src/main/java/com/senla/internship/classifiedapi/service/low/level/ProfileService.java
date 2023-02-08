package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the {@link IProfileService} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class ProfileService implements IProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile save(Profile profile) {
        log.info("Profile has been saved");
        return profileRepository.save(profile);
    }

    @Override
    public boolean existsByPhone(String phone) {
        log.info("Checked if user exists by phone");
        return profileRepository.existsByPhone(phone);
    }
}
