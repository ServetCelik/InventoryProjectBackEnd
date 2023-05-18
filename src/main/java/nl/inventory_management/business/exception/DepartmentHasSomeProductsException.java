package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepartmentHasSomeProductsException extends ResponseStatusException {
    public DepartmentHasSomeProductsException() {
        super(HttpStatus.BAD_REQUEST, "DEPARTMENT_CANNOT_BE_DELETED_WHEN_IT_HAS_PRODUCTS");
    }
}
