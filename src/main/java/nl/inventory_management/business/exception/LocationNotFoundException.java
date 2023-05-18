package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LocationNotFoundException extends ResponseStatusException {
    public LocationNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "LOCATION_NOT_FOUND");
    }
}
