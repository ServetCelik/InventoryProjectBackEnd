package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.UserConverter;
import nl.inventory_management.business.domain.User;
import nl.inventory_management.business.exception.UserNotFoundException;
import nl.inventory_management.business.interfaces.GetUserUseCase;
import nl.inventory_management.repository.UserRepository;
import nl.inventory_management.repository.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private final UserRepository userRepository;
    @Transactional
    @Override
    public List<User> getAllUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(UserConverter::convertToModel).toList();
    }
    @Transactional
    @Override
    public User getUserById(Long id) {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException();
        }
        UserEntity entity =  userRepository.findById(id).orElse(null);
        return UserConverter.convertToModel(entity);

    }
    @Transactional
    @Override
    public User getUserByName(String name) {
        if(!userRepository.existsByUserName(name)){
            throw new UserNotFoundException();
        }
        UserEntity entity =  userRepository.findByUserName(name);
        return UserConverter.convertToModel(entity);
    }

}
