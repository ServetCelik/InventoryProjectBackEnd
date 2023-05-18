package nl.inventory_management.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepartmentDescriptionTooShortException extends ResponseStatusException {
    public DepartmentDescriptionTooShortException() {
        super(HttpStatus.BAD_REQUEST, "DEPARTMENT_DESCRIPTION_IS_TOO_SHORT");
    }
}
