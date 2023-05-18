package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.DepartmentAlreadyExistsException;
import nl.inventory_management.business.exception.LocationNameAlreadyExistsException;
import nl.inventory_management.business.exception.UnsupportedLocationFieldsException;
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
class CreateLocationUseCaseImplTest {
    @Mock
    private LocationRepository locationRepositoryMock;
    @InjectMocks
    private CreateLocationUseCaseImpl createLocationUseCase;

    @Test
    void createLocationShouldReturnALocation() {
        // Arrange
        LocationEntity request = LocationEntity.builder().name("Eindhoven").address("North").maxPallet(888).build();
        LocationEntity respond = LocationEntity.builder().id(1L).name("Eindhoven").address("North").maxPallet(888).build();

        when(locationRepositoryMock.save(request)).thenReturn(respond);
        when(locationRepositoryMock.existsByName("Eindhoven")).thenReturn(false);

        Location requestedLocation = Location.builder().name("Eindhoven").address("North").maxPallet(888).build();

        // Act
        Location expectedResult = createLocationUseCase.createLocation(requestedLocation);

        assertEquals( 888,expectedResult.getMaxPallet());

        // Assert
        verify(locationRepositoryMock).save(request);
        verify(locationRepositoryMock).existsByName("Eindhoven");

    }

    @Test
    void createLocation_ShouldReturnLocationNameAlreadyExistsException() {
        // Arrange
        Location request = Location.builder().id(1L).name("Eindhoven").address("North").maxPallet(888).build();
        when(locationRepositoryMock.existsByName("Eindhoven")).thenReturn(true);

        // Act
        Exception exception = assertThrows(LocationNameAlreadyExistsException.class, () -> {
            createLocationUseCase.createLocation(request);
        });

        String expectedMessage = "LOCATION_NAME_ALREADY_EXISTS";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(locationRepositoryMock).existsByName("Eindhoven");
    }
    @Test
    void createLocation_ShouldReturnUnsupportedLocationFieldsException() {
        // Arrange
        Location request = Location.builder().id(1L).name("E").address("North").maxPallet(888).build();

        // Act
        Exception exception = assertThrows(UnsupportedLocationFieldsException.class, () -> {
            createLocationUseCase.createLocation(request);
        });

        String expectedMessage = "LOCATION_NAME_AND_ADDRESS_LENGTH_MUST_BE_BETWEEN_2_AND_30_AND_MAX_PALLET_COULD_BE_1000";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
        verify(locationRepositoryMock).existsByName("E");
    }
}