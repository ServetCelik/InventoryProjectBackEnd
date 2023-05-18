package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.repository.entity.DepartmentEntity;

public final class DepartmentConverter {
    private DepartmentConverter() {

    }

    public static Department convertToModel(DepartmentEntity department) {
        return Department.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }

    public static DepartmentEntity convertToEntity(Department department) {
        return DepartmentEntity.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}