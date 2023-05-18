package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import nl.inventory_management.repository.entity.LocationEntity;
import nl.inventory_management.repository.entity.PalletEntity;
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
class CreatePalletUseCaseImplTest {
    @Mock
    private PalletRepository palletRepositoryMock;
    @InjectMocks
    private CreatePalletUseCaseImpl createPalletUseCase;
    @Test
    void createPalletShouldReturnAPallet() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        LocationEntity locationEntity= LocationEntity.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        ProductEntity productEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        PalletEntity requestEntity = PalletEntity.builder().amount(500).productEntity(productEntity).locationEntity(locationEntity).build();
        PalletEntity respondEntity = PalletEntity.builder().id(1L).amount(500).productEntity(productEntity).locationEntity(locationEntity).build();

        when(palletRepositoryMock.save(requestEntity)).thenReturn(respondEntity);

        Department electronicsDep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Location location= Location.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        Product product = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(electronicsDep).build();
        Pallet requestedPallet = Pallet.builder().amount(500).product(product).location(location).build();

        // Act
        Pallet expectedResult = createPalletUseCase.createPallet(requestedPallet);

        // Assert
        assertEquals( "Apple",expectedResult.getProduct().getName());
        verify(palletRepositoryMock).save(requestEntity);

    }
}