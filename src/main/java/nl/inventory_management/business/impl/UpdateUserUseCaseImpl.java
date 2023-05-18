package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.exception.UserNameAlreadyExistsException;
import nl.inventory_management.business.interfaces.UpdateUserUseCase;
import nl.inventory_management.business.conventer.UserConverter;
import nl.inventory_management.business.domain.User;
import nl.inventory_management.business.exception.UserNotFoundException;
import nl.inventory_management.repository.UserRepository;
import nl.inventory_management.repository.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public User updateUser(User request) {

        if (!userRepository.existsById(request.getId())){
            throw new UserNotFoundException();
        }
        if (userRepository.existsByUserName(request.getUserName()) &&
                !userRepository.findByUserName(request.getUserName()).getId().equals(request.getId())){
                throw new UserNameAlreadyExistsException();
        }

        return save(request);
    }
    private User save(User user){
        UserEntity entity = UserConverter.convertToEntity(user);
        UserEntity respond = userRepository.save(entity);
        return UserConverter.convertToModel(respond);
    }

}

