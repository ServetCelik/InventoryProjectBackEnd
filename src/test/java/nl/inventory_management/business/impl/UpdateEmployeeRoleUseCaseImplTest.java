package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.business.domain.EmployeeRole;
import nl.inventory_management.business.domain.RoleEnum;
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

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UpdateEmployeeRoleUseCaseImplTest {

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    private UpdateEmployeeRoleUseCaseImpl updateEmployeeRoleUseCase;

    @Test
    void updateEmployee_ShouldReturnEmployeeNotFoundException() {
        // Arrange
        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();

        when(employeeRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            updateEmployeeRoleUseCase.updateRole(employee);
        });

        String expectedMessage = "Employee not found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(employeeRepositoryMock).existsById(1L);
    }

    @Test
    void updateEmployee_ShouldReturnEmployeeObject() {
        // Arrange
        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        Employee employee = Employee.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build();

        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();

        when(employeeRepositoryMock.existsById(1L)).thenReturn(true);
        when(employeeRepositoryMock.existsByEmail("SS@Mail.com")).thenReturn(true);
        when(employeeRepositoryMock.findByEmail("SS@Mail.com")).thenReturn(employeeEntity);
        when(employeeRepositoryMock.save(employeeEntity)).thenReturn(employeeEntity);

        // Act
        Employee expectedResult = updateEmployeeRoleUseCase.updateRole(employee);

        // Assert
        assertEquals(employeeEntity.getRoles().size(),expectedResult.getRoles().size());
        verify(employeeRepositoryMock).existsById(1L);
    }

}