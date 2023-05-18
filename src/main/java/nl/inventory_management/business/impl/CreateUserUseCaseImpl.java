package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.CreateUserUseCase;
import nl.inventory_management.business.conventer.UserConverter;
import nl.inventory_management.business.exception.EmailAlreadyExistsException;
import nl.inventory_management.business.exception.UnsupportedUserFieldsException;
import nl.inventory_management.business.exception.UserNameAlreadyExistsException;
import nl.inventory_management.repository.EmployeeRepository;
import nl.inventory_management.repository.UserRepository;
import nl.inventory_management.business.domain.User;
import nl.inventory_management.repository.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    @Transactional
    @Override
    public User createUser(User request) {
        if (userRepository.existsByUserName(request.getUserName())){
            throw  new UserNameAlreadyExistsException();
        }
        if(employeeRepository.existsByEmail(request.getEmployee().getEmail())){
            throw new EmailAlreadyExistsException();
        }
        if(request.getUserName().length()<2||request.getUserName().length()>30){
            throw new UnsupportedUserFieldsException();
        }


        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);


        return save(request);
    }
    private User save(User user){
        UserEntity entity = UserConverter.convertToEntity(user);
        UserEntity respond = userRepository.save(entity);
        return UserConverter.convertToModel(respond);
    }
}
