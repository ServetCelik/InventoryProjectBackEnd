package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNameAlreadyExistsException extends ResponseStatusException {
    public ProductNameAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "PRODUCT_NAME_ALREADY_EXISTS");
    }
}