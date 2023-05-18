package nl.inventory_management.controller.converter;

import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.controller.dto.GetEmployeeResponse;

import java.util.stream.Collectors;

public final class EmployeeConverter {
    private EmployeeConverter(){

    }
    public static GetEmployeeResponse employeeToGetEmployeeResponse(Employee employee) {
        return GetEmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .roles(employee.getRoles().stream().map(x->x.getRole().toString()).collect(Collectors.toSet()))
                .build();
    }
}
