package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.DeleteLocationUseCase;
import nl.inventory_management.business.exception.LocationHasSomePalletsException;
import nl.inventory_management.business.exception.LocationNotFoundException;
import nl.inventory_management.controller.dto.DeleteResponse;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.entity.LocationEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteLocationUseCaseImpl implements DeleteLocationUseCase {
    private final LocationRepository locationRepository;
    private final PalletRepository palletRepository;
    @Transactional
    @Override
    public DeleteResponse deleteLocation(Long id) {
        if (!locationRepository.existsById(id)){
            throw  new LocationNotFoundException();
        }
        Optional<LocationEntity> entity = locationRepository.findById(id);
        boolean hasPallets = palletRepository.existsByLocationEntity(entity);
        if (hasPallets){
            throw  new LocationHasSomePalletsException();
        }
        delete(id);

        return DeleteResponse.builder()
                .result("Location with id:" + id + "is deleted...")
                .build();
    }

    private void delete(Long id){
         locationRepository.deleteById(id);
    }
}
