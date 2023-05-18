package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmployeeNotFoundException extends ResponseStatusException {
    public EmployeeNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Employee not found");
    }
}
