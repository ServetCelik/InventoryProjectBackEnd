package nl.inventory_management.controller.converter;

import nl.inventory_management.business.domain.Location;
import nl.inventory_management.controller.dto.CreateLocationResponse;
import nl.inventory_management.controller.dto.GetLocationResponse;

public final class LocationConverter {
    private LocationConverter(){

    }
    public static GetLocationResponse locationToGetLocationResponse(Location location) {
        return GetLocationResponse.builder().build().builder()
                .id(location.getId())
                .name(location.getName())
                .address(location.getAddress())
                .maxPallet(location.getMaxPallet())
                .build();
    }
    public static CreateLocationResponse locationToCreateLocationResponse(Location location) {
        return CreateLocationResponse.builder()
                .id(location.getId())
                .build();
    }
}
