package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.PalletNotFoundException;
import nl.inventory_management.business.exception.ProductNotFoundException;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import nl.inventory_management.repository.entity.ProductEntity;
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
class GetProductUseCaseImplTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private GetProductUseCaseImpl getProductUseCase;

    @Test
    void getAllProducts_ShouldReturnAllProductsCreated() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        ProductEntity firstProductEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        ProductEntity secondProductEntity = ProductEntity.builder().id(2L).name("Samsung").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        ProductEntity thirdProductEntity = ProductEntity.builder().id(3L).name("Htc").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();

        when(productRepositoryMock.findAll()).thenReturn(List.of(firstProductEntity,secondProductEntity,thirdProductEntity));

        // Act
        List<Product> actualResult = getProductUseCase.getAllProducts();
        String expectedResult = "Htc";

        // Assert
        assertEquals(actualResult.get(2).getName(),expectedResult);
        verify(productRepositoryMock).findAll();
    }

    @Test
    void getProductById_ShouldReturnAProduct() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        ProductEntity productEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();

        when(productRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(productEntity));
        when(productRepositoryMock.existsById(1L)).thenReturn(true);

        // Act
        Product actualResult = getProductUseCase.getProductById(1L);

        Department dep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product product = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(dep).build();

        // Assert
        assertEquals(actualResult,product);
        verify(productRepositoryMock).findById(1L);
        verify(productRepositoryMock).existsById(1L);
    }

    @Test
    void getProductByName_ShouldReturnAProduct() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        ProductEntity productEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();

        when(productRepositoryMock.findByName("Apple")).thenReturn(productEntity);
        when(productRepositoryMock.existsByName("Apple")).thenReturn(true);

        // Act
        Product actualResult = getProductUseCase.getProductByName("Apple");

        Department dep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product product = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(dep).build();

        // Assert
        assertEquals(actualResult,product);
        verify(productRepositoryMock).findByName("Apple");
        verify(productRepositoryMock).existsByName("Apple");
    }

    @Test
    void getProductById_ShouldReturnProductNotFoundException() {
        // Arrange
        when(productRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            getProductUseCase.getProductById(1L);
        });

        String expectedMessage = "PRODUCT_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepositoryMock).existsById(1L);
    }

    @Test
    void getProductByName_ShouldReturnProductNotFoundException() {
        // Arrange
        when(productRepositoryMock.existsByName("Apple")).thenReturn(false);

        // Act
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            getProductUseCase.getProductByName("Apple");
        });

        String expectedMessage = "PRODUCT_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepositoryMock).existsByName("Apple");
    }
}