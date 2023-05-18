package nl.inventory_management.business.impl;

import nl.inventory_management.business.exception.InvalidCredentialsException;
import nl.inventory_management.business.exception.UserNotFoundException;
import nl.inventory_management.business.interfaces.AccessTokenEncoder;
import nl.inventory_management.repository.UserRepository;
import nl.inventory_management.repository.entity.EmployeeEntity;
import nl.inventory_management.repository.entity.EmployeeRoleEntity;
import nl.inventory_management.repository.entity.RoleEntityEnum;
import nl.inventory_management.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private AccessTokenEncoder accessTokenEncoderMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @InjectMocks
    private LoginUserUseCaseImpl loginUserUseCase;

    @Test
    void loginUser_ShouldReturnUserNotFoundExceptionWhenUserIsNull() {
        // Arrange
        when(userRepositoryMock.findByUserName("Servet")).thenReturn(null);

        // Act
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            loginUserUseCase.loginUser("Servet","1234");
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepositoryMock).findByUserName("Servet");
    }
    @Test
    void loginUser_ShouldReturnInvalidCredentialsExceptionWhenPasswordsDontMatch() {
        // Arrange
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();
        UserEntity respondEntity = UserEntity.builder().id(1L).userName("Servet").password("1234").employeeEntity(employeeEntity).build();

        when(userRepositoryMock.findByUserName("Servet")).thenReturn(respondEntity);
        when(passwordEncoderMock.matches("6789","1234")).thenReturn(false);

        // Act
        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
            loginUserUseCase.loginUser("Servet","6789");
        });

        String expectedMessage = "Invalid Credentials";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(passwordEncoderMock).matches("6789","1234");
    }

}