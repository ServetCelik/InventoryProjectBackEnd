package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.User;

public interface CreateUserUseCase {
    User createUser(User user);
}
