package nl.inventory_management.business.impl;

import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.entity.LocationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteLocationUseCaseImplTest {
    @Mock
    private LocationRepository locationRepositoryMock;
    @Mock
    private PalletRepository palletRepositoryMock;
    @InjectMocks
    private DeleteLocationUseCaseImpl deleteLocationUseCaseMock;

    @Test
    void deleteLocation() {
        // Arrange
        LocationEntity locationEntity = LocationEntity.builder().name("Eindhoven").address("North").maxPallet(888).build();

        when(locationRepositoryMock.existsById(1L)).thenReturn(true);
        when(locationRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(locationEntity));
        when(palletRepositoryMock.existsByLocationEntity(Optional.ofNullable(locationEntity))).thenReturn(false);

        // Act
        deleteLocationUseCaseMock.deleteLocation(1L);

        // Assert
        verify(locationRepositoryMock, times(1)).deleteById(1L);
    }
}