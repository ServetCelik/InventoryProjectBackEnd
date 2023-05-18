package nl.inventory_management.controller.converter;

import nl.inventory_management.controller.dto.CreateDepartmentResponse;
import nl.inventory_management.controller.dto.GetDepartmentResponse;
import nl.inventory_management.business.domain.Department;

public final class DepartmentConverter {
    private DepartmentConverter(){

    }
    public static GetDepartmentResponse departmentToGetDepartmentResponse(Department department) {
        return GetDepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
    public static CreateDepartmentResponse departmentToCreateDepartmentResponse(Department department) {
        return CreateDepartmentResponse.builder()
                .id(department.getId())
                .build();
    }
}
