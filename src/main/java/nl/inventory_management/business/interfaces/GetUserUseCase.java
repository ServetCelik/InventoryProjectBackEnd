package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.User;

import java.util.List;

public interface GetUserUseCase {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByName(String name);
}
