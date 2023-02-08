package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.exception.AdvertisementNotFoundException;
import com.senla.internship.classifiedapi.exception.DataNotFoundException;
import com.senla.internship.classifiedapi.model.account.Profile;
import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.repository.AdvertisementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {
    @Mock private AdvertisementRepository advertisementRepository;
    private IAdvertisementService advertisementService;
    private Advertisement advertisement;
    private Profile profile;

    @BeforeEach
    void setUp() {
        this.advertisementService = new AdvertisementService(advertisementRepository);
        this.profile = Profile.builder()
                .id(1L)
                .name("John")
                .phone("+79055168642")
                .balance(new BigDecimal("0"))
                .rating(5)
                .advertisements(Collections.emptyList())
                .messages(Collections.emptyList())
                .build();
        this.advertisement = Advertisement.builder()
                .id(1L)
                .type(null)
                .title("")
                .condition(null)
                .brand(null)
                .description("")
                .active(true)
                .vip(false)
                .location(null)
                .profile(profile)
                .comments(Collections.emptyList())
                .build();
    }

    @Test
    void shouldFindByIdAndProfileWhenNotNull() {
        given(advertisementRepository.findByIdAndProfile(advertisement.getId(), profile))
                .willReturn(Optional.ofNullable(advertisement));

        advertisementService.findByIdAndProfile(advertisement.getId(), profile);

        verify(advertisementRepository).findByIdAndProfile(advertisement.getId(), profile);
    }

    @Test
    void shouldThrowExceptionWhenFindByIdAndProfileIsEqualToNull() {
        String message = String.format("Advertisement with id %d not found", advertisement.getId());

        assertThatThrownBy(() -> advertisementService.findByIdAndProfile(advertisement.getId(), profile))
                .isInstanceOf(AdvertisementNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void shouldFindByIdWhenNotNull() {
        given(advertisementRepository.findById(advertisement.getId()))
                .willReturn(Optional.ofNullable(advertisement));

        advertisementService.findById(advertisement.getId());

        verify(advertisementRepository).findById(advertisement.getId());
    }

    @Test
    void shouldThrowExceptionWhenFindByIdIsEqualToNull() {
        String message = String.format("Advertisement with id %d not found", advertisement.getId());

        assertThatThrownBy(() -> advertisementService.findById(advertisement.getId()))
                .isInstanceOf(AdvertisementNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void shouldSave() {
        advertisementService.save(advertisement);

        ArgumentCaptor<Advertisement> advertisementArgumentCaptor =
                ArgumentCaptor.forClass(Advertisement.class);

        verify(advertisementRepository).save(advertisementArgumentCaptor.capture());

        Advertisement capturedAdvertisement = advertisementArgumentCaptor.getValue();

        assertThat(capturedAdvertisement).isEqualTo(advertisement);
    }

    @Test
    void deleteAdvertisementByIdAndProfileAndActiveIsTrue() {
        advertisementService
                .deleteAdvertisementByIdAndProfileAndActiveIsTrue(advertisement.getId(), profile);

        verify(advertisementRepository)
                .deleteAdvertisementByIdAndProfileAndActiveIsTrue(advertisement.getId(), profile);
    }

    @Test
    void shouldFindAllByTitle() {
        String title = "smth";

        given(advertisementRepository.findAllByTitle(title))
                .willReturn(Collections.singletonList(advertisement));

        advertisementService.findAllByTitle(title);

        verify(advertisementRepository)
                .findAllByTitle(title);
    }

    @Test
    void shouldThrowExceptionWhenFindAll() {
        String message = "Data not found";
        String title = "smth";

        assertThatThrownBy(() -> advertisementService.findAllByTitle(title))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void shouldFindAllByTitleAndPrice() {
        String title = "smth";
        BigDecimal minPrice = new BigDecimal("10000");
        BigDecimal maxPrice = new BigDecimal("20000");

        given(advertisementRepository
                .findAllByTitleAndFilterByPrice(
                title, minPrice, maxPrice))
                .willReturn(Collections.singletonList(advertisement));

        advertisementService.findAllByTitleAndMinPriceAndMaxPrice(title, minPrice, maxPrice);

        verify(advertisementRepository)
                .findAllByTitleAndFilterByPrice(
                        title, minPrice, maxPrice);
    }

    @Test
    void shouldThrowExceptionWhenFindAllByTitleAndPrice() {
        String title = "smth";
        String message = "Data not found";
        BigDecimal minPrice = new BigDecimal("10000");
        BigDecimal maxPrice = new BigDecimal("20000");

        assertThatThrownBy(() -> advertisementService.findAllByTitleAndMinPriceAndMaxPrice(title, minPrice, maxPrice))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void shouldFindAllByProfileAndActiveIsFalse() {
        given(advertisementRepository.findAllByProfileAndActiveIsFalse(profile))
                .willReturn(Collections.singletonList(advertisement));

        advertisementService.findAllByProfileAndActiveIsFalse(profile);

        verify(advertisementRepository).findAllByProfileAndActiveIsFalse(profile);
    }

    @Test
    void shouldThrowExceptionWhenFindAllByProfileAndActiveIsFalse() {
        String message = "Data not found";

        assertThatThrownBy(() -> advertisementService.findAllByProfileAndActiveIsFalse(profile))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void findAllByProfileAndActiveIsTrue() {
        given(advertisementRepository.findAllByProfileAndActiveIsTrue(profile))
                .willReturn(Collections.singletonList(advertisement));

        advertisementService.findAllByProfileAndActiveIsTrue(profile);

        verify(advertisementRepository).findAllByProfileAndActiveIsTrue(profile);
    }

    @Test
    void shouldThrowExceptionWhenFindAllByProfileAndActiveIsTrue() {
        String message = "Data not found";

        assertThatThrownBy(() -> advertisementService.findAllByProfileAndActiveIsTrue(profile))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage(message);
    }

    @Test
    void deleteById() {
        advertisementService.deleteById(advertisement.getId());

        verify(advertisementRepository).deleteById(advertisement.getId());
    }
}