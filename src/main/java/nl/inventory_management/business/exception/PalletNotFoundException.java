package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PalletNotFoundException extends ResponseStatusException {
    public PalletNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "PALLET_NOT_FOUND");
    }
}
