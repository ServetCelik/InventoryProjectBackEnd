package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Employee;

import java.util.List;

public interface GetEmployeeUseCase {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee getEmployeeByName(String name);
}
