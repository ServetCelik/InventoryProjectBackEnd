package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.*;
import nl.inventory_management.business.exception.DepartmentAlreadyExistsException;
import nl.inventory_management.business.exception.EmailAlreadyExistsException;
import nl.inventory_management.business.exception.UnsupportedUserFieldsException;
import nl.inventory_management.business.exception.UserNameAlreadyExistsException;
import nl.inventory_management.repository.EmployeeRepository;
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
class CreateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void createUser_ShouldReturnDepartmentAlreadyExistsException() {
        // Arrange
        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();
        User user = User.builder().id(1L).userName("Servet").password("1234").employee(employee).build();

        when(userRepositoryMock.existsByUserName("Servet")).thenReturn(true);

        // Act
        Exception exception = assertThrows(UserNameAlreadyExistsException.class, () -> {
            createUserUseCase.createUser(user);
        });

        String expectedMessage = "USERNAME_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepositoryMock).existsByUserName("Servet");
    }
    @Test
    void createUser_ShouldReturnEmailAlreadyExistsException() {
        // Arrange
        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();
        User user = User.builder().id(1L).userName("Servet").password("1234").employee(employee).build();

        when(employeeRepositoryMock.existsByEmail("SS@Mail.com")).thenReturn(true);

        // Act
        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            createUserUseCase.createUser(user);
        });

        String expectedMessage = "EMAIL_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(employeeRepositoryMock).existsByEmail("SS@Mail.com");
    }

    @Test
    void createUser_ShouldReturnUnsupportedUserFieldsException() {
        // Arrange
        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();
        User user = User.builder().id(1L).userName("S").password("1234").employee(employee).build();

        // Act
        Exception exception = assertThrows(UnsupportedUserFieldsException.class, () -> {
            createUserUseCase.createUser(user);
        });

        String expectedMessage = "USERNAME_LENGTH_MUST_BE_BETWEEN_2_AND_30";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }


}


















//    @Test
//    void createUserShouldReturnUser() {
//        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
//        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();
//        UserEntity requestEntity = UserEntity.builder().userName("Srvt").password("1234").employeeEntity(employeeEntity).build();
//        UserEntity respondEntity = UserEntity.builder().id(1L).userName("Srvt").password("1234").employeeEntity(employeeEntity).build();
//
//
//        when(userRepositoryMock.save(requestEntity)).thenReturn(respondEntity);
//        when(passwordEncoderMock.encode(requestEntity.getPassword())).thenReturn("4567");
//
//        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
//        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();
//        User requestedUser = User.builder().userName("Srvt").password("1234").employee(employee).build();
//        User expectedResult = createUserUseCase.createUser(requestedUser);
//
//        assertEquals(expectedResult.getUserName(),"Srvt");
//
//        verify(userRepositoryMock).save(requestEntity);