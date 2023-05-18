package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LocationHasSomePalletsException extends ResponseStatusException {
    public LocationHasSomePalletsException() {
        super(HttpStatus.BAD_REQUEST, "LOCATION_CANNOT_BE_DELETED_WHEN_IT_HAS_PALLETS");
    }
}
