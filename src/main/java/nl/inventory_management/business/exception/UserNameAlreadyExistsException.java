package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNameAlreadyExistsException extends ResponseStatusException {
    public UserNameAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_EXISTS");
    }
}
