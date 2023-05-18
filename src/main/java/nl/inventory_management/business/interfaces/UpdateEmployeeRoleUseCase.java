package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Employee;

public interface UpdateEmployeeRoleUseCase {
    Employee updateRole(Employee request);
}
