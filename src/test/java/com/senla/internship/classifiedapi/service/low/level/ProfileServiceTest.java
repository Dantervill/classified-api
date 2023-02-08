package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.repository.ProfileRepository;
import com.senla.internship.classifiedapi.service.low.level.IProfileService;
import com.senla.internship.classifiedapi.service.low.level.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
    @Mock private ProfileRepository profileRepository;
    private IProfileService profileService;
    private Profile profile;

    @BeforeEach
    void setUp() {
        this.profileService = new ProfileService(profileRepository);
        this.profile = Profile.builder()
                .id(1L)
                .name("John")
                .phone("+79055168642")
                .balance(new BigDecimal("0"))
                .rating(5)
                .advertisements(Collections.emptyList())
                .messages(Collections.emptyList())
                .build();
    }

    @Test
    void shouldSaveWhenNotExistsByPhone() {
        profileService.save(profile);

        ArgumentCaptor<Profile> profileArgumentCaptor =
                ArgumentCaptor.forClass(Profile.class);

        verify(profileRepository).save(profileArgumentCaptor.capture());

        Profile capturedProfile = profileArgumentCaptor.getValue();

        assertThat(capturedProfile).isEqualTo(profile);
    }

    @Test
    void shouldReturnTrueWhenExistsByPhone() {
        given(profileRepository.existsByPhone(profile.getPhone()))
                .willReturn(true);

        profileService.existsByPhone(profile.getPhone());

        verify(profileRepository).existsByPhone(profile.getPhone());
    }

    @Test
    void shouldReturnFalseWhenNotExistsByPhone() {
        given(profileRepository.existsByPhone(profile.getPhone()))
                .willReturn(false);

        profileService.existsByPhone(profile.getPhone());

        verify(profileRepository).existsByPhone(profile.getPhone());
    }
}