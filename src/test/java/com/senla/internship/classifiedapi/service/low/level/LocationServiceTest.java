package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.advertisement.Location;
import com.senla.internship.classifiedapi.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {
    @Mock private LocationRepository locationRepository;
    private LocationService locationService;
    private Location location;

    @BeforeEach
    void setUp() {
        this.locationService = new LocationService(locationRepository);
        this.location = new Location(1L, "Moscow", "Moscovskaya Oblast'");
    }

    @Test
    void save() {
        locationService.save(location);

        ArgumentCaptor<Location> locationArgumentCaptor =
                ArgumentCaptor.forClass(Location.class);

        verify(locationRepository).save(locationArgumentCaptor.capture());

        Location capturedLocation = locationArgumentCaptor.getValue();

        assertThat(capturedLocation).isEqualTo(location);
    }

    @Test
    void deleteById() {
        locationService.deleteById(location.getId());

        verify(locationRepository).deleteById(location.getId());
    }
}