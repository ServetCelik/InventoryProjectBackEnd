package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnsupportedProductFieldsException extends ResponseStatusException {
    public UnsupportedProductFieldsException() {
        super(HttpStatus.BAD_REQUEST
                , "PRODUCT_NAME_LENGTH_MUST_BE_BETWEEN_2_AND_50_" +
                        "AND_DESCRIPTION_LENGTH_MUST_BE_BETWEEN_2_AND_500");
    }
}
