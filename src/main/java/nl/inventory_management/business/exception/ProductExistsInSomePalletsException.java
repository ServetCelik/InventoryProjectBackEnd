package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductExistsInSomePalletsException extends ResponseStatusException {
    public ProductExistsInSomePalletsException() {
        super(HttpStatus.BAD_REQUEST, "PRODUCT_EXISTS_IN_SOME_PALLETS");
    }
}
