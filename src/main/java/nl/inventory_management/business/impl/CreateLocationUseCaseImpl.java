package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.CreateLocationUseCase;
import nl.inventory_management.business.conventer.LocationConverter;
import nl.inventory_management.business.exception.LocationNameAlreadyExistsException;
import nl.inventory_management.business.exception.UnsupportedLocationFieldsException;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.repository.entity.LocationEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateLocationUseCaseImpl implements CreateLocationUseCase {
    private final LocationRepository locationRepository;

    @Override
    public Location createLocation(Location request) {

        if (locationRepository.existsByName(request.getName())){
            throw new LocationNameAlreadyExistsException();
        }
        if(request.getName().length()<2 ||request.getName().length()>30||
                request.getAddress().length()<2||request.getAddress().length()>30
                ||request.getMaxPallet()>1000){
            throw new UnsupportedLocationFieldsException();
        }

        return save(request);
    }
    private Location save(Location location){
        LocationEntity entity = LocationConverter.convertToEntity(location);
        LocationEntity respond = locationRepository.save(entity);
        return LocationConverter.convertToModel(respond);
    }

}

