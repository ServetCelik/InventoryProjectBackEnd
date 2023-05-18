package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.*;
import nl.inventory_management.business.exception.ProductNotFoundException;
import nl.inventory_management.business.exception.UserNotFoundException;
import nl.inventory_management.repository.UserRepository;
import nl.inventory_management.repository.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void getAllUsers_ShouldReturnAllUsersCreated() {
        // Arrange
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();
        UserEntity userEntity = UserEntity.builder().id(1L).userName("Srvt").password("1234").employeeEntity(employeeEntity).build();
        EmployeeEntity secondEmployeeEntity = EmployeeEntity.builder().id(2L).name("Jack").lastName("Sparrow").email("JS@Mail.com").roles(Set.of(employeeRoleEntity)).build();
        UserEntity secondUserEntity = UserEntity.builder().id(2L).userName("Captain").password("1234").employeeEntity(secondEmployeeEntity).build();

        when(userRepositoryMock.findAll()).thenReturn(List.of(userEntity,secondUserEntity));

        // Act
        List<User> actualResult = getUserUseCase.getAllUsers();
        String expectedResult = "SS@Mail.com";

        // Assert
        assertEquals(actualResult.get(0).getEmployee().getEmail(),expectedResult);
        verify(userRepositoryMock).findAll();
    }

    @Test
    void getUserById_ShouldReturnAUser() {
        // Arrange
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();
        UserEntity userEntity = UserEntity.builder().id(1L).userName("Srvt").password("1234").employeeEntity(employeeEntity).build();

        when(userRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(userEntity));
        when(userRepositoryMock.existsById(1L)).thenReturn(true);

        // Act
        User actualResult = getUserUseCase.getUserById(1L);

        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();
        User user = User.builder().id(1L).userName("Srvt").password("1234").employee(employee).build();

        // Assert
        assertEquals(user, actualResult);
        verify(userRepositoryMock).findById(1L);
        verify(userRepositoryMock).existsById(1L);
    }

    @Test
    void getUserByName_ShouldReturnAUser() {
        // Arrange
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();
        UserEntity userEntity = UserEntity.builder().id(1L).userName("Srvt").password("1234").employeeEntity(employeeEntity).build();

        when(userRepositoryMock.findByUserName("Srvt")).thenReturn(userEntity);
        when(userRepositoryMock.existsByUserName("Srvt")).thenReturn(true);

        // Act
        User actualResult = getUserUseCase.getUserByName("Srvt");

        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();
        User user = User.builder().id(1L).userName("Srvt").password("1234").employee(employee).build();

        // Assert
        assertEquals(user, actualResult);
        verify(userRepositoryMock).findByUserName("Srvt");
        verify(userRepositoryMock).existsByUserName("Srvt");
    }

    @Test
    void getUserById_ShouldReturnUserNotFoundException() {
        // Arrange
        when(userRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            getUserUseCase.getUserById(1L);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepositoryMock).existsById(1L);
    }

    @Test
    void getUserByName_ShouldReturnUserNotFoundException() {
        // Arrange
        when(userRepositoryMock.existsByUserName("Servet")).thenReturn(false);

        // Act
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            getUserUseCase.getUserByName("Servet");
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepositoryMock).existsByUserName("Servet");
    }


}