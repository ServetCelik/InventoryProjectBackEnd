package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.ProductNameAlreadyExistsException;
import nl.inventory_management.business.exception.ProductNotFoundException;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import nl.inventory_management.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseImplTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private UpdateProductUseCaseImpl updateProductUseCaseImpl;

    @Test
    void updateProductShouldReturnAProduct() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        ProductEntity productEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();


        when(productRepositoryMock.existsById(1L)).thenReturn(true);
        when(productRepositoryMock.existsByName("Apple")).thenReturn(false);
        when(productRepositoryMock.save(productEntity)).thenReturn(productEntity);


        Department electronicsDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product requestedProduct = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(electronicsDep).build();

        // Act
        Product expectedResult = updateProductUseCaseImpl.updateProduct(requestedProduct);

        // Assert
        assertEquals( "Apple",expectedResult.getName());

        verify(productRepositoryMock).existsById(1L);
        verify(productRepositoryMock).existsByName("Apple");
        verify(productRepositoryMock).save(productEntity);

    }
    @Test
    void updateProduct_ShouldReturnProductNotFoundException() {
        // Arrange
        Department electronicsDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product request = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(electronicsDep).build();

        when(productRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            updateProductUseCaseImpl.updateProduct(request);
        });

        String expectedMessage = "PRODUCT_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepositoryMock).existsById(1L);
    }

    @Test
    void updateProduct_ShouldReturnProductNameAlreadyExistsException() {
        // Arrange
        Department electronicsDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product request = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(electronicsDep).build();
        ProductEntity foundEntity = ProductEntity.builder().id(2L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();

        when(productRepositoryMock.existsById(1L)).thenReturn(true);
        when(productRepositoryMock.existsByName("Apple")).thenReturn(true);
        when(productRepositoryMock.findByName("Apple")).thenReturn(foundEntity);


        // Act
        Exception exception = assertThrows(ProductNameAlreadyExistsException.class, () -> {
            updateProductUseCaseImpl.updateProduct(request);
        });

        String expectedMessage = "PRODUCT_NAME_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));

        verify(productRepositoryMock).existsById(1L);
        verify(productRepositoryMock).existsByName("Apple");
        verify(productRepositoryMock).findByName("Apple");
    }
}