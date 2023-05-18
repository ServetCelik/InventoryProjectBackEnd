package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.exception.LocationNameAlreadyExistsException;
import nl.inventory_management.business.interfaces.UpdateLocationUseCase;
import nl.inventory_management.business.conventer.LocationConverter;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.LocationNotFoundException;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.entity.LocationEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateLocationUseCaseImpl implements UpdateLocationUseCase {
    private final LocationRepository locationRepository;

    @Override
    public Location updateLocation(Location request) {

        if (!locationRepository.existsById(request.getId())){
            throw new LocationNotFoundException();
        }
        if (locationRepository.existsByName(request.getName()) &&
            !locationRepository.findByName(request.getName()).getId().equals(request.getId())){
                throw new LocationNameAlreadyExistsException();
        }

        return save(request);
    }
    private Location save(Location location){
        LocationEntity entity = LocationConverter.convertToEntity(location);
        LocationEntity respond = locationRepository.save(entity);
        return LocationConverter.convertToModel(respond);
    }

}

