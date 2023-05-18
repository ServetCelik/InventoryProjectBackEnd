package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.repository.entity.EmployeeEntity;

import java.util.stream.Collectors;

public final class EmployeeConverter {
    private EmployeeConverter() {

    }

    public static Employee convertToModel(EmployeeEntity employee) {
        return Employee.builder()
                .id(employee.getId())
                .name(employee.getName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .roles(employee.getRoles().stream()
                        .map(EmployeeRoleConverter::convertToModel).collect(Collectors.toSet()))
                .build();
    }

    public static EmployeeEntity convertToEntity(Employee employee) {
        return EmployeeEntity.builder()
                .id(employee.getId())
                .name(employee.getName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .roles(employee.getRoles().stream()
                        .map(EmployeeRoleConverter::convertToEntity).collect(Collectors.toSet()))
                .build();
    }
}

//.map(x -> EmployeeRoleConverter.convertToModel(x)).collect(Collectors.toSet()))
//.map(x -> EmployeeRoleConverter.convertToEntity(x)).collect(Collectors.toSet()))