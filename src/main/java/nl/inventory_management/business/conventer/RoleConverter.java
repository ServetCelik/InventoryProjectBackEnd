package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.RoleEnum;
import nl.inventory_management.repository.entity.RoleEntityEnum;

public final class RoleConverter {
    private RoleConverter(){

    }
    public static RoleEnum convertToModel(RoleEntityEnum role) {
        return RoleEnum.valueOf(role.name());
    }
    public static RoleEntityEnum convertToEntity(RoleEnum role) {
        return RoleEntityEnum.valueOf(role.name());
    }
}