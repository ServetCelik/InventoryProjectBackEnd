package nl.inventory_management.business.interfaces;

import nl.inventory_management.controller.dto.DeleteUserResponse;

public interface DeleteUserUseCase {
    DeleteUserResponse deleteUser(Long request);


}
