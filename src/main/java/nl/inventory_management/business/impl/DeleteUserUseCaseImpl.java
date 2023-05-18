package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.DeleteUserUseCase;
import nl.inventory_management.business.exception.UserNotFoundException;
import nl.inventory_management.controller.dto.DeleteUserResponse;
import nl.inventory_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public DeleteUserResponse deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw  new UserNotFoundException();
        }
        delete(id);

        return DeleteUserResponse.builder()
                .result("User with id:" + id + "is deleted...")
                .build();
    }

    private void delete(Long id){
         userRepository.deleteById(id);
    }
}

