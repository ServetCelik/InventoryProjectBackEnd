package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Location;

public interface UpdateLocationUseCase {
    Location updateLocation(Location request);
}
