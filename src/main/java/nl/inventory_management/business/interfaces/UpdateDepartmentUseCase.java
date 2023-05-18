package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Department;

public interface UpdateDepartmentUseCase {
    Department updateDepartment(Department request);
}
