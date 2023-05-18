package nl.inventory_management.business.impl;

import nl.inventory_management.repository.InvoiceRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePalletUseCaseImplTest {
    @Mock
    private PalletRepository palletRepositoryMock;
    @Mock
    private InvoiceRepository invoiceRepositoryMock;
    @InjectMocks
    private DeletePalletUseCaseImpl deletePalletUseCaseMock;
    @Test
    void deletePallet() {
        // Arrange
        DepartmentEntity electronicsDepEnt = DepartmentEntity.builder().id(1L).name("Electronics").description("All kind of electronics").build();
        LocationEntity locationEntity= LocationEntity.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        ProductEntity productEntity = ProductEntity.builder().id(1L).name("Apple").description("none").weight(50).width(60).length(70).height(80).departmentEntity(electronicsDepEnt).build();
        PalletEntity requestEntity = PalletEntity.builder().amount(500).productEntity(productEntity).locationEntity(locationEntity).build();

        when(palletRepositoryMock.existsById(1L)).thenReturn(true);
        when(palletRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(requestEntity));

        // Act
        deletePalletUseCaseMock.deletePallet(1L);

        // Assert
        verify(palletRepositoryMock, times(1)).deleteById(1L);
        verify(palletRepositoryMock).existsById(1L);
    }
}