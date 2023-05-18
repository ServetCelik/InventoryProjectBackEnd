package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Product;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import nl.inventory_management.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFilteredProductsUseCaseImplTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private GetFilteredProductsUseCaseImpl getFilteredProductsUseCase;
    @Test
    void getFilteredProductsShouldReturnAList() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        ProductEntity firstProductEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        ProductEntity thirdProductEntity = ProductEntity.builder().id(3L).name("Philips").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();

        when(productRepositoryMock.findByNameContaining("p")).thenReturn(List.of(firstProductEntity,thirdProductEntity));

        // Act
        List<Product> actualResult = getFilteredProductsUseCase.getFilteredProducts("p","","");
        Integer expectedResult = 2;

        // Assert
        assertEquals(actualResult.size(),expectedResult);
        verify(productRepositoryMock).findByNameContaining("p");
    }
}