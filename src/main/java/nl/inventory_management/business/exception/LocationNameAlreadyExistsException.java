package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LocationNameAlreadyExistsException extends ResponseStatusException {
    public LocationNameAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "LOCATION_NAME_ALREADY_EXISTS");
    }
}