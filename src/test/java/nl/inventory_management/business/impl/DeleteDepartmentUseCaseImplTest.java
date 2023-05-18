package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDepartmentUseCaseImplTest {

    @Mock
    private DepartmentRepository departmentRepositoryMock;
    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private DeleteDepartmentUseCaseImpl deleteDepartmentUseCaseMock;
    @Test
    void DepartmentShouldDeleteDepartment() {
        // Arrange
        DepartmentEntity departmentEntity = DepartmentEntity.builder().id(1L).name("Electronic").description("All kind of products").build();

        when(departmentRepositoryMock.existsById(1L)).thenReturn(true);
        when(departmentRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(departmentEntity));
        when(productRepositoryMock.existsByDepartmentEntity(Optional.ofNullable(departmentEntity))).thenReturn(false);

        // Act
        deleteDepartmentUseCaseMock.deleteDepartment(1L);

        // Assert
        verify(departmentRepositoryMock, times(1)).deleteById(1L);
    }
}