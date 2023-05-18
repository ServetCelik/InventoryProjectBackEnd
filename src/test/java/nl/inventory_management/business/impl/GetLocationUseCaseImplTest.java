package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.EmployeeNotFoundException;
import nl.inventory_management.business.exception.LocationNotFoundException;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.entity.LocationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetLocationUseCaseImplTest {
    @Mock
    private LocationRepository locationRepositoryMock;
    @InjectMocks
    private GetLocationUseCaseImpl getLocationUseCase;

    @Test
    void getAllLocations_ShouldReturnAllLocationsCreated() {
        // Arrange
        LocationEntity eindhovenEntity = LocationEntity.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        LocationEntity arnhemEntity = LocationEntity.builder().id(2L).name("Arnhem Depot").address("Arnhem").maxPallet(888).build();

        when(locationRepositoryMock.findAll()).thenReturn(List.of(eindhovenEntity, arnhemEntity));

        // Act
        List<Location> actualResult = getLocationUseCase.getAllLocations();
        Location eindhoven = Location.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        Location arnhem = Location.builder().id(2L).name("Arnhem Depot").address("Arnhem").maxPallet(888).build();
        List<Location> expectedResult = List.of(eindhoven, arnhem);

        // Assert
        assertEquals(actualResult,expectedResult);
        verify(locationRepositoryMock).findAll();
    }

    @Test
    void getLocationById_ShouldReturnAnObject() {
        // Arrange
        LocationEntity eindhovenEntity = LocationEntity.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        when(locationRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(eindhovenEntity));
        when(locationRepositoryMock.existsById(1L)).thenReturn(true);

        // Act
        Location actualResult = getLocationUseCase.getLocationById(1L);
        Location eindhoven = Location.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();

        // Assert
        assertEquals( actualResult,eindhoven);
        verify(locationRepositoryMock).findById(1L);

    }

    @Test
    void getLocationByName_ShouldReturnAnObject() {
        // Arrange
        LocationEntity eindhovenEntity = LocationEntity.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();
        when(locationRepositoryMock.findByName("Eindhoven Depot")).thenReturn(eindhovenEntity);
        when(locationRepositoryMock.existsByName("Eindhoven Depot")).thenReturn(true);

        // Act
        Location actualResult = getLocationUseCase.getLocationByName("Eindhoven Depot");
        Location eindhoven = Location.builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(999).build();

        // Assert
        assertEquals( actualResult,eindhoven);
        verify(locationRepositoryMock).findByName("Eindhoven Depot");
    }
    @Test
    void getLocationByName_ShouldReturnLocationNotFoundException() {
        // Arrange
        when(locationRepositoryMock.existsByName("Eindhoven")).thenReturn(false);

        // Act
        Exception exception = assertThrows(LocationNotFoundException.class, () -> {
            getLocationUseCase.getLocationByName("Eindhoven");
        });

        String expectedMessage = "LOCATION_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(locationRepositoryMock).existsByName("Eindhoven");
    }

    @Test
    void getLocationById_ShouldReturnLocationNotFoundException() {
        // Arrange
        when(locationRepositoryMock.existsById(1L)).thenReturn(false);

        // Act
        Exception exception = assertThrows(LocationNotFoundException.class, () -> {
            getLocationUseCase.getLocationById(1L);
        });

        String expectedMessage = "LOCATION_NOT_FOUND";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(locationRepositoryMock).existsById(1L);
    }



}