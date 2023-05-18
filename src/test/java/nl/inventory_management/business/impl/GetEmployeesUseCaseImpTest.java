package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.business.domain.EmployeeRole;
import nl.inventory_management.business.domain.RoleEnum;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.business.exception.EmployeeNotFoundException;
import nl.inventory_management.repository.EmployeeRepository;
import nl.inventory_management.repository.entity.EmployeeEntity;
import nl.inventory_management.repository.entity.EmployeeRoleEntity;
import nl.inventory_management.repository.entity.RoleEntityEnum;
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
class GetEmployeesUseCaseImpTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private GetEmployeeUseCaseImp getEmployeesUseCaseImp;

    @Test
    void getAllEmployees_ShouldReturnAllUsersCreated() {
        // Arrange
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();
        EmployeeEntity secondEmployeeEntity = EmployeeEntity.builder().id(2L).name("Jack").lastName("Sparrow").email("JS@Mail.com").roles(Set.of(employeeRoleEntity)).build();

        when(employeeRepository.findAll()).thenReturn(List.of(employeeEntity,secondEmployeeEntity));

        // Act
        List<Employee> actualResult = getEmployeesUseCaseImp.getAllEmployees();
        String expectedResult = "Servet";

        // Assert
        assertEquals( actualResult.get(0).getName(),expectedResult);
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById_ShouldReturnAnEmployee() {
        // Arrange
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(employeeEntity));
        when(employeeRepository.existsById(1L)).thenReturn(true);

        // Act
        Employee actualResult = getEmployeesUseCaseImp.getEmployeeById(1L);

        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();

        // Assert
        assertEquals(actualResult,employee);
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).existsById(1L);
    }

    @Test
    void getEmployeeByName_ShouldReturnAnEmployee() {
        // Arrange
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();

        when(employeeRepository.findByName("Servet")).thenReturn(employeeEntity);
        when(employeeRepository.existsByName("Servet")).thenReturn(true);

        // Act
        Employee actualResult = getEmployeesUseCaseImp.getEmployeeByName("Servet");

        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();

        // Assert
        assertEquals(actualResult,employee);
        verify(employeeRepository).findByName("Servet");
        verify(employeeRepository).existsByName("Servet");
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeNotFoundException() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            getEmployeesUseCaseImp.getEmployeeById(1L);
        });

        String expectedMessage = "Employee not found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(employeeRepository).existsById(1L);
    }
    @Test
    void getEmployeeByName_ShouldReturnEmployeeNotFoundException() {
        // Arrange
        when(employeeRepository.existsByName("Servet")).thenReturn(false);

        // Act
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            getEmployeesUseCaseImp.getEmployeeByName("Servet");
        });

        String expectedMessage = "Employee not found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(employeeRepository).existsByName("Servet");
    }


}