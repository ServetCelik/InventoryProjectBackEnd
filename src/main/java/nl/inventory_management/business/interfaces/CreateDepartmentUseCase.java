package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Department;

public interface CreateDepartmentUseCase {
    Department createDepartment(Department request);
}
