package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.exception.DepartmentAlreadyExistsException;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UpdateDepartmentUseCaseImplTest {

    @Mock
    private DepartmentRepository departmentRepositoryMock;
    @InjectMocks
    private UpdateDepartmentUseCaseImpl updateDepartmentUseCase;

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void updateDepartmentShouldReturnADepartment() {
        // Arrange
        DepartmentEntity request = DepartmentEntity.builder().id(1L).name("Electronic").description("All kind of products").build();
        DepartmentEntity respond = DepartmentEntity.builder().id(1L).name("Electronic").description("All kind of products").build();

        when(departmentRepositoryMock.save(request)).thenReturn(respond);
        when(departmentRepositoryMock.existsByName("Electronic")).thenReturn(true);
        when(departmentRepositoryMock.existsById(1L)).thenReturn(true);
        when(departmentRepositoryMock.findByName("Electronic")).thenReturn(request);

        Department firstDepartment = Department.builder().id(1L).name("Electronic").description("All kind of products").build();

        // Act
        Department expectedResult = updateDepartmentUseCase.updateDepartment(firstDepartment);

        // Assert
        assertEquals(respond.getId(),expectedResult.getId());

        verify(departmentRepositoryMock).save(request);
        verify(departmentRepositoryMock).existsById(1L);
        verify(departmentRepositoryMock).existsByName("Electronic");
        verify(departmentRepositoryMock).findByName("Electronic");

    }

    @Test
    void updateDepartment_ShouldReturnDepartmentNotFoundException() {
        // Arrange
        Department request = Department.builder().id(1L).name("Electronics").description("All kind of products").build();
        when(departmentRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(DepartmentNotFoundException.class, () -> {
            updateDepartmentUseCase.updateDepartment(request);
        });

        String expectedMessage = "DEPARTMENT_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(departmentRepositoryMock).existsById(1L);
    }

    //@MockitoSettings(strictness = Strictness.WARN)
    @Test
    void updateDepartment_ShouldReturnDepartmentAlreadyExistsException() {
        // Arrange
        Department request = Department.builder().id(1L).name("Electronics").description("All kind of products").build();
        DepartmentEntity departmentEntity = DepartmentEntity.builder().id(2L).name("Electronics").description("All kind of products").build();

        when(departmentRepositoryMock.existsById(1L)).thenReturn(true);
        when(departmentRepositoryMock.existsByName("Electronics")).thenReturn(true);
        when(departmentRepositoryMock.findByName("Electronics")).thenReturn(departmentEntity);

        // Act
        Exception exception = assertThrows(DepartmentAlreadyExistsException.class, () -> {
            updateDepartmentUseCase.updateDepartment(request);
        });

        String expectedMessage = "DEPARTMENT_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(departmentRepositoryMock).existsById(1L);
    }

}