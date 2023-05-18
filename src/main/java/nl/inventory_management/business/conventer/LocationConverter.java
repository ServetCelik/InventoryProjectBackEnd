package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.Location;
import nl.inventory_management.repository.entity.LocationEntity;

public final class LocationConverter {
    private LocationConverter() {

    }

    public static Location convertToModel(LocationEntity location) {
        return Location.builder()
                .id(location.getId())
                .name(location.getName())
                .address(location.getAddress())
                .maxPallet(location.getMaxPallet())
                .build();
    }

    public static LocationEntity convertToEntity(Location location) {
        return LocationEntity.builder()
                .id(location.getId())
                .name(location.getName())
                .address(location.getAddress())
                .maxPallet(location.getMaxPallet())
                .build();
    }
}