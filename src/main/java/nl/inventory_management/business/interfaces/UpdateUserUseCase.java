package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.User;

public interface UpdateUserUseCase {
    User updateUser(User request);
}
