package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.LocationNameAlreadyExistsException;
import nl.inventory_management.business.exception.LocationNotFoundException;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.entity.LocationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UpdateLocationUseCaseImplTest {
    @Mock
    private LocationRepository locationRepositoryMock;
    @InjectMocks
    private UpdateLocationUseCaseImpl updateLocationUseCaseMock;
    @Test
    void updateLocationShouldReturnALocation() {
        // Arrange
        LocationEntity locationEntity = LocationEntity.builder().id(1L).name("Eindhoven").address("North").maxPallet(888).build();

        when(locationRepositoryMock.save(locationEntity)).thenReturn(locationEntity);
        when(locationRepositoryMock.existsByName("Eindhoven")).thenReturn(false);
        when(locationRepositoryMock.existsById(1L)).thenReturn(true);

        Location requestedLocation = Location.builder().id(1L).name("Eindhoven").address("North").maxPallet(888).build();

        // Act
        Location expectedResult = updateLocationUseCaseMock.updateLocation(requestedLocation);

        // Assert
        assertEquals( 888,expectedResult.getMaxPallet());

        verify(locationRepositoryMock).save(locationEntity);
        verify(locationRepositoryMock).existsByName("Eindhoven");
        verify(locationRepositoryMock).existsById(1L);

    }
    @Test
    void updateLocationShouldReturnALocationNotFoundException() {
        // Arrange
        Location request = Location.builder().id(1L).name("Eindhoven").address("North").maxPallet(888).build();
        when(locationRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(LocationNotFoundException.class, () -> {
            updateLocationUseCaseMock.updateLocation(request);
        });

        String expectedMessage = "LOCATION_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(locationRepositoryMock).existsById(1L);

    }
    @Test
    void updateLocationShouldReturnLocationNameAlreadyExistsException() {
        // Arrange
        Location request = Location.builder().id(1L).name("Eindhoven").address("North").maxPallet(888).build();
        LocationEntity respondLocation = LocationEntity.builder().id(2L).name("Eindhoven").address("North").maxPallet(888).build();

        when(locationRepositoryMock.existsById(1L)).thenReturn(true);
        when(locationRepositoryMock.existsByName("Eindhoven")).thenReturn(true);
        when(locationRepositoryMock.findByName("Eindhoven")).thenReturn(respondLocation);

        // Act
        Exception exception = assertThrows(LocationNameAlreadyExistsException.class, () -> {
            updateLocationUseCaseMock.updateLocation(request);
        });

        String expectedMessage = "LOCATION_NAME_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));

        verify(locationRepositoryMock).existsByName("Eindhoven");
        verify(locationRepositoryMock).findByName("Eindhoven");
        verify(locationRepositoryMock).existsById(1L);

    }
}