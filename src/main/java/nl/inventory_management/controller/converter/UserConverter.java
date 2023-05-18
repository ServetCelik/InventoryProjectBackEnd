package nl.inventory_management.controller.converter;

import nl.inventory_management.business.domain.User;
import nl.inventory_management.controller.dto.CreateUserResponse;
import nl.inventory_management.controller.dto.GetUserResponse;

import java.util.stream.Collectors;

public final class UserConverter {
    private UserConverter(){

    }
    public static GetUserResponse userToGetUserResponse(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getEmployee().getName())
                .lastName(user.getEmployee().getLastName())
                .email(user.getEmployee().getEmail())
                .roles(user.getEmployee().getRoles().stream().map(x->x.getRole().toString()).collect(Collectors.toSet()))
                .build();
    }
    public static CreateUserResponse userToCreateUserResponse(User user) {
        return CreateUserResponse.builder()
                .userId(user.getId())
                .build();
    }
}
