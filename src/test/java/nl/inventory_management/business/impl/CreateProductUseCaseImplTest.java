package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.LocationNameAlreadyExistsException;
import nl.inventory_management.business.exception.ProductNameAlreadyExistsException;
import nl.inventory_management.business.exception.UnsupportedProductFieldsException;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import nl.inventory_management.repository.entity.ProductEntity;
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
class CreateProductUseCaseImplTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private CreateProductUseCaseImpl createProductUseCase;
   //@MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void createProductShouldReturnAProduct() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        ProductEntity requestEntity = ProductEntity.builder().name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        ProductEntity respondEntity = ProductEntity.builder().id(2L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();

        when(productRepositoryMock.save(requestEntity)).thenReturn(respondEntity);
        when(productRepositoryMock.existsByName("Apple")).thenReturn(false);

        Department electronicsDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product requestedProduct = Product.builder().name("Apple").description("none").weight(50).width(60).length(70).height(80).department(electronicsDep).build();

        // Act
        Product expectedResult = createProductUseCase.createProduct(requestedProduct);

        // Assert
        assertEquals( "Apple",expectedResult.getName());
        verify(productRepositoryMock).save(requestEntity);
        verify(productRepositoryMock).existsByName("Apple");

    }

    @Test
    void createProduct_ShouldReturnProductNameAlreadyExistsException() {
        // Arrange
        Department electronicsDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product request = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(electronicsDep).build();
        when(productRepositoryMock.existsByName("Apple")).thenReturn(true);

        // Act
        Exception exception = assertThrows(ProductNameAlreadyExistsException.class, () -> {
            createProductUseCase.createProduct(request);
        });

        String expectedMessage = "PRODUCT_NAME_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepositoryMock).existsByName("Apple");
    }

    @Test
    void createProduct_ShouldReturnUnsupportedProductFieldsException() {
        // Arrange
        Department electronicsDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Product request = Product.builder().id(1L).name("A").description("none").weight(50).width(60).length(70).height(80).department(electronicsDep).build();

        // Act
        Exception exception = assertThrows(UnsupportedProductFieldsException.class, () -> {
            createProductUseCase.createProduct(request);
        });

        String expectedMessage = "PRODUCT_NAME_LENGTH_MUST_BE_BETWEEN_2_AND_50_AND_DESCRIPTION_LENGTH_MUST_BE_BETWEEN_2_AND_500";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
}