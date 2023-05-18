package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Product;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import nl.inventory_management.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseImplTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private PalletRepository palletRepositoryMock;
    @InjectMocks
    private DeleteProductUseCaseImpl deleteProductUseCaseMock;

    @Test
    void deleteProduct() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        ProductEntity respondEntity = ProductEntity.builder().id(2L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();

        when(productRepositoryMock.existsById(2L)).thenReturn(true);
        when(productRepositoryMock.findById(2L)).thenReturn(Optional.ofNullable(respondEntity));
        when(palletRepositoryMock.existsByProductEntity(Optional.ofNullable(respondEntity))).thenReturn(false);

        // Act
        deleteProductUseCaseMock.deleteProduct(2L);

        // Assert
        verify(productRepositoryMock, times(1)).deleteById(2L);
    }
}