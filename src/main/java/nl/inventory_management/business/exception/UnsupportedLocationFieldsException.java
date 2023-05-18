package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnsupportedLocationFieldsException extends ResponseStatusException {
    public UnsupportedLocationFieldsException() {
        super(HttpStatus.BAD_REQUEST
                , "LOCATION_NAME_AND_ADDRESS_LENGTH_MUST_BE_BETWEEN_2_AND_30_" +
                        "AND_MAX_PALLET_COULD_BE_1000");
    }
}
