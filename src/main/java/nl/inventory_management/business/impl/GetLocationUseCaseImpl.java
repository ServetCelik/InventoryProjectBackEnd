package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.LocationConverter;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.LocationNotFoundException;
import nl.inventory_management.business.interfaces.GetLocationUseCase;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.entity.LocationEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class GetLocationUseCaseImpl implements GetLocationUseCase {
    private final LocationRepository locationRepository;
    @Override
    public List<Location> getAllLocations() {
        List<LocationEntity> entities = locationRepository.findAll();
        return entities.stream()
                .map(LocationConverter::convertToModel).toList();
    }
    @Override
    public Location getLocationById(Long id) {
        if(!locationRepository.existsById(id)){
            throw new LocationNotFoundException();
        }
        LocationEntity entity = locationRepository.findById(id).orElse(null);
        return LocationConverter.convertToModel(entity);

    }

    @Override
    public Location getLocationByName(String name) {
        if(!locationRepository.existsByName(name)){
            throw new LocationNotFoundException();
        }
        LocationEntity entity =  locationRepository.findByName(name);
        return LocationConverter.convertToModel(entity);
    }

}
