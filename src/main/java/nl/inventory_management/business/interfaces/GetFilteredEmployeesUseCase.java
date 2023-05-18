package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Employee;

import java.util.List;

public interface GetFilteredEmployeesUseCase {
    List<Employee> getFilteredEmployees(String name, String lastName, String email, String role);
}
