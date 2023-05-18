package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.EmployeeNotFoundException;
import nl.inventory_management.business.exception.PalletNotFoundException;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPalletUseCaseImplTest {
    @Mock
    private PalletRepository palletRepositoryMock;
    @InjectMocks
    private GetPalletUseCaseImpl getPalletUseCase;

    @Test
    void getAllPallets_ShouldReturnAllPalletsCreated() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        LocationEntity locationEntity= LocationEntity.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        ProductEntity productEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        PalletEntity firstPallet = PalletEntity.builder().id(1L).amount(500).productEntity(productEntity).locationEntity(locationEntity).build();
        PalletEntity secondPallet = PalletEntity.builder().id(2L).amount(600).productEntity(productEntity).locationEntity(locationEntity).build();
        PalletEntity thirdPallet = PalletEntity.builder().id(3L).amount(700).productEntity(productEntity).locationEntity(locationEntity).build();

        when(palletRepositoryMock.findAll()).thenReturn(List.of(firstPallet,secondPallet,thirdPallet));

        // Act
        List<Pallet> actualResult = getPalletUseCase.getAllPallets();
        int expectedResult = 600;

        // Assert
        assertEquals(actualResult.get(1).getAmount(),expectedResult);
        verify(palletRepositoryMock).findAll();
    }

    @Test
    void getPalletById_ShouldReturnAnPallet() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        LocationEntity locationEntity= LocationEntity.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        ProductEntity productEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        PalletEntity firstPallet = PalletEntity.builder().id(1L).amount(500).productEntity(productEntity).locationEntity(locationEntity).build();

        when(palletRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(firstPallet));
        when(palletRepositoryMock.existsById(1L)).thenReturn(true);

        // Act
        Pallet actualResult = getPalletUseCase.getPalletById(1L);

        Department dep = Department.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        Location loc= Location.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        Product product = Product.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).department(dep).build();
        Pallet pallet = Pallet.builder().id(1L).amount(500).product(product).location(loc).build();

        // Assert
        assertEquals(actualResult,pallet);
        verify(palletRepositoryMock).findById(1L);
        verify(palletRepositoryMock).existsById(1L);

    }

    @Test
    void getPalletById_ShouldReturnPalletNotFoundException() {
        // Arrange
        when(palletRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(PalletNotFoundException.class, () -> {
            getPalletUseCase.getPalletById(1L);
        });

        String expectedMessage = "PALLET_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(palletRepositoryMock).existsById(1L);
    }
}