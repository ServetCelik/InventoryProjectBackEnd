package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.exception.DepartmentAlreadyExistsException;
import nl.inventory_management.business.exception.DepartmentDescriptionTooShortException;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateDepartmentUseCaseImplTest {
    @Mock
    private DepartmentRepository departmentRepositoryMock;
    @InjectMocks
    private CreateDepartmentUseCaseImpl createDepartmentUseCase;

    @Test
    void createDepartmentShouldReturnADepartment() {
        // Arrange
        DepartmentEntity request = DepartmentEntity.builder().name("Electronic").description("All kind of products").build();
        DepartmentEntity respond = DepartmentEntity.builder().id(1L).name("Electronic").description("All kind of products").build();

        when(departmentRepositoryMock.save(request)).thenReturn(respond);
        when(departmentRepositoryMock.existsByName("Electronic")).thenReturn(false);

        Department firstDepartment = Department.builder().name("Electronic").description("All kind of products").build();

        // Act
        Department expectedResult = createDepartmentUseCase.createDepartment(firstDepartment);

        // Assert
        assertEquals(1L,expectedResult.getId());
        verify(departmentRepositoryMock).save(request);

    }

    @Test
    void createDepartment_ShouldReturnDepartmentAlreadyExistsException() {
        // Arrange
        Department request = Department.builder().name("Electronics").description("All kind of products").build();
        when(departmentRepositoryMock.existsByName("Electronics")).thenReturn(true);

        // Act
        Exception exception = assertThrows(DepartmentAlreadyExistsException.class, () -> {
            createDepartmentUseCase.createDepartment(request);
        });

        String expectedMessage = "DEPARTMENT_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(departmentRepositoryMock).existsByName("Electronics");
    }

    @Test
    void createDepartment_ShouldReturnDepartmentDescriptionTooShortException() {
        // Arrange
        Department request = Department.builder().name("Electronics").description("none").build();

        // Act
        Exception exception = assertThrows(DepartmentDescriptionTooShortException.class, () -> {
            createDepartmentUseCase.createDepartment(request);
        });

        String expectedMessage = "DEPARTMENT_DESCRIPTION_IS_TOO_SHORT";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}