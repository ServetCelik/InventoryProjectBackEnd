package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDepartmentUseCaseImplTest {
    @Mock
    private DepartmentRepository departmentRepositoryMock;
    @InjectMocks
    private GetDepartmentUseCaseImpl getDepartmentUseCase;

    @Test
    void getAllDepartments_ShouldReturnAllDepartmentsCreated() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        DepartmentEntity sportDepEnt = DepartmentEntity.builder().id(1L).name("Sport").description("All kind of sport").build();

        when(departmentRepositoryMock.findAll()).thenReturn(List.of(electronicsDepEnt, sportDepEnt));

        List<Department> actualResult = getDepartmentUseCase.getAllDepartments();
        Department electronics = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Department sport = Department.builder().id(1L).name("Sport").description("All kind of sport").build();
        List<Department> expectedResult = List.of(electronics, sport);
        assertEquals( actualResult,expectedResult);

        verify(departmentRepositoryMock).findAll();
    }

    @Test
    void getDepartmentById_ShouldReturnAnObject() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        when(departmentRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(electronicsDepEnt));
        when(departmentRepositoryMock.existsById(1L)).thenReturn(true);

        // Act
        Department actualResult = getDepartmentUseCase.getDepartmentById(1L);
        Department expectingDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        assertEquals( actualResult,expectingDep);

        verify(departmentRepositoryMock).findById(1L);

    }


    @Test
    void getDepartmentByName_ShouldReturnAnObject() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        when(departmentRepositoryMock.findByName("Electronics")).thenReturn(electronicsDepEnt);
        when(departmentRepositoryMock.existsByName("Electronics")).thenReturn(true);

        // Act
        Department actualResult = getDepartmentUseCase.findDepartmentByName("Electronics");
        Department expectingDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();

        // Assert
        assertEquals( actualResult,expectingDep);
        verify(departmentRepositoryMock).findByName("Electronics");
    }

    @Test
    void getDepartmentByName_ShouldReturnDepartmentNotFoundException() {
        // Arrange
        when(departmentRepositoryMock.existsByName("Electronics")).thenReturn(false);

        // Act
        Exception exception = assertThrows(DepartmentNotFoundException.class, () -> {
            getDepartmentUseCase.findDepartmentByName("Electronics");
        });

        String expectedMessage = "DEPARTMENT_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
       verify(departmentRepositoryMock).existsByName("Electronics");
    }

        @Test
    void getDepartmentById_ShouldReturnDepartmentNotFoundException() {
            // Arrange
        when(departmentRepositoryMock.existsById(1L)).thenReturn(false);

            // Act
            Exception exception = assertThrows(DepartmentNotFoundException.class, () -> {
                getDepartmentUseCase.getDepartmentById(1L);
            });

            String expectedMessage = "DEPARTMENT_NOT_FOUND";
            String actualMessage = exception.getMessage();

            // Assert
            assertTrue(actualMessage.contains(expectedMessage));
            verify(departmentRepositoryMock).existsById(1L);
    }
}