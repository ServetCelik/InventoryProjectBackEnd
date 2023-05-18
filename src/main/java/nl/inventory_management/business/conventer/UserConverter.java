package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.User;
import nl.inventory_management.repository.entity.UserEntity;

public final class UserConverter {
    private UserConverter(){

    }
    public static User convertToModel(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .employee(EmployeeConverter.convertToModel(user.getEmployeeEntity()))
                .build();
    }
    public static UserEntity convertToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .employeeEntity(EmployeeConverter.convertToEntity(user.getEmployee()))
                .build();
    }
}