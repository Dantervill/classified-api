package com.senla.internship.classifiedapi.service.low.level;

import com.senla.internship.classifiedapi.model.advertisement.Location;

/**
 * Core interface which processes location-specific-data.
 * @author Vlas Nagibin
 * @version 1.0
 */
public interface ILocationService {

    /**
     * Saves a location.
     * @param location the location
     * @return the location
     */
    Location save(Location location);

    /**
     * Deletes a location by id.
     * @param id the id
     */
    void deleteById(Long id);
}
