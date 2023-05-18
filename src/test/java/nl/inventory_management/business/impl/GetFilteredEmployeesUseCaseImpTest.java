package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.business.domain.EmployeeRole;
import nl.inventory_management.business.domain.RoleEnum;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFilteredEmployeesUseCaseImpTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private GetFilteredEmployeesUseCaseImp getEmployeesUseCaseImp;
    @Test
    void getFilteredEmployeesShouldReturnAList() {
        // Arrange
        EmployeeRole employeeRole = EmployeeRole.builder().id(1L).role(RoleEnum.ADMIN).build();
        EmployeeRoleEntity employeeRoleEntity = EmployeeRoleEntity.builder().id(1L).role(RoleEntityEnum.ADMIN).build();
        RoleEntityEnum roleEntityEnum = RoleEntityEnum.ADMIN;
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(3L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRoleEntity)).build();

        when(employeeRepository.findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role("er","St","SS",roleEntityEnum)).thenReturn(List.of(employeeEntity));

        // Act
        List<Employee> actualResult = getEmployeesUseCaseImp.getFilteredEmployees("er","St","SS","ADMIN");
        List<Employee> expectedResult = List.of(Employee.builder().id(3L).name("Servet").lastName("Steel").email("SS@Mail.com").roles(Set.of(employeeRole)).build());

        // Assert
        assertEquals( actualResult.get(0).getName(),expectedResult.get(0).getName());
        verify(employeeRepository).findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role("er","St","SS",roleEntityEnum);
    }
}