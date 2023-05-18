package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Location;

import java.util.List;

public interface GetLocationUseCase {
    List<Location> getAllLocations();
    Location getLocationById(Long id);
    Location getLocationByName(String name);
}
