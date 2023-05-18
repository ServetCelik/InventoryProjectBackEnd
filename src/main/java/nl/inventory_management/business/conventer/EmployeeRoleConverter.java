package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.EmployeeRole;
import nl.inventory_management.repository.entity.EmployeeRoleEntity;

public final class EmployeeRoleConverter {
    private EmployeeRoleConverter() {

    }

    public static EmployeeRole convertToModel(EmployeeRoleEntity role) {
        return EmployeeRole.builder()
                .id(role.getId())
                .role(RoleConverter.convertToModel(role.getRole()))
                //.employee(EmployeeConverter.convertToModel(role.getEmployee()))
                .build();
    }

    public static EmployeeRoleEntity convertToEntity(EmployeeRole role) {
        return EmployeeRoleEntity.builder()
                .id(role.getId())
                .role(RoleConverter.convertToEntity(role.getRole()))
                //.employee(EmployeeConverter.convertToEntity(role.getEmployee()))
                .build();
    }
}