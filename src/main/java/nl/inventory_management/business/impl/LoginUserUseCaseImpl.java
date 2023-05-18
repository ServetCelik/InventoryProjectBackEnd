package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.AccessTokenEncoder;
import nl.inventory_management.business.interfaces.LoginUserUseCase;
import nl.inventory_management.business.conventer.UserConverter;
import nl.inventory_management.business.exception.InvalidCredentialsException;
import nl.inventory_management.business.exception.UserNotFoundException;
import nl.inventory_management.business.domain.AccessToken;
import nl.inventory_management.repository.UserRepository;
import nl.inventory_management.business.domain.User;
import nl.inventory_management.repository.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;
    @Override
    public String loginUser(String name,String password){
        User user ;

        try{
            user = findUser(name);
        }catch (NullPointerException e){
            throw new UserNotFoundException();
        }

        if (!matchesPassword(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return generateAccessToken(user);
    }
    private User findUser(String name){

        UserEntity entity = userRepository.findByUserName(name);
        return UserConverter.convertToModel(entity);
    }
    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    private String generateAccessToken(User user) {
        Long userId = user.getId();
        List<String> roles = user.getEmployee().getRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.getUserName())
                        .roles(roles)
                        .userId(userId)
                        .build());
    }
}
