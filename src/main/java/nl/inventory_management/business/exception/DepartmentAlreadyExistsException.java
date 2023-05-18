package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepartmentAlreadyExistsException extends ResponseStatusException {
    public DepartmentAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "DEPARTMENT_ALREADY_EXISTS");
    }
}