package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnsupportedUserFieldsException extends ResponseStatusException {
    public UnsupportedUserFieldsException() {
        super(HttpStatus.BAD_REQUEST
                , "USERNAME_LENGTH_MUST_BE_BETWEEN_2_AND_30");
    }
}
