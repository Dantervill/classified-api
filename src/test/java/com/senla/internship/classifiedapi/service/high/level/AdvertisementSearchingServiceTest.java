package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.model.advertisement.Advertisement;
import com.senla.internship.classifiedapi.service.low.level.AdvertisementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdvertisementSearchingServiceTest {
    @Mock private AdvertisementService advertisementService;
    private AdvertisementSearchingService advertisementSearchingService;

    @BeforeEach
    void setUp() {
        this.advertisementSearchingService =
                new AdvertisementSearchingService(advertisementService);
    }

    @Test
    void searchAdvertisements() {
        String title = "smth";

        given(advertisementService.findAllByTitle(title))
                .willReturn(Collections.singletonList(new Advertisement()));

        advertisementSearchingService.search(title);

        verify(advertisementService).findAllByTitle(title);
    }

    @Test
    void searchAdvertisementsFilterByPrice() {
        String title = "smth";
        BigDecimal min = new BigDecimal("150");
        BigDecimal max = new BigDecimal("300");

        given(advertisementService.findAllByTitleAndMinPriceAndMaxPrice(title, min, max))
                .willReturn(Collections.singletonList(new Advertisement()));

        advertisementSearchingService
                .searchAndFilter(title, min, max);

        verify(advertisementService).findAllByTitleAndMinPriceAndMaxPrice(title, min, max);
    }
}