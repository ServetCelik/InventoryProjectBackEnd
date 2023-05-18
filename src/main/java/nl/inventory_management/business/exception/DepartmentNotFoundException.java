package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepartmentNotFoundException extends ResponseStatusException {
    public DepartmentNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "DEPARTMENT_NOT_FOUND");
    }
}
