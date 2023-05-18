package nl.inventory_management.business.interfaces;

import nl.inventory_management.controller.dto.DeleteResponse;

public interface DeleteDepartmentUseCase {
    DeleteResponse deleteDepartment(Long id);

}
