package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.advertisement.Location;
import com.senla.internship.classifiedapi.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the {@link ILocationService} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class LocationService implements ILocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(Location location) {
        log.info("Location has been saved");
        return locationRepository.save(location);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Location has been deleted by id");
        locationRepository.deleteById(id);
    }
}
