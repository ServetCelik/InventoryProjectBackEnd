package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Department;

import java.util.List;

public interface GetDepartmentUseCase {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department findDepartmentByName(String name);
}
