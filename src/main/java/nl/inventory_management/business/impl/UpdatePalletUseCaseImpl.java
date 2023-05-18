package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.UpdatePalletUseCase;
import nl.inventory_management.business.conventer.PalletConverter;
import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.business.exception.PalletNotFoundException;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.entity.PalletEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdatePalletUseCaseImpl implements UpdatePalletUseCase {
    private final PalletRepository palletRepository;

    @Override
    public Pallet updatePallet(Pallet request) {

        if (!palletRepository.existsById(request.getId())){
            throw new PalletNotFoundException();
        }

        return save(request);
    }
    private Pallet save(Pallet pallet){
        PalletEntity entity = PalletConverter.convertToEntity(pallet);
        PalletEntity respond = palletRepository.save(entity);
        return PalletConverter.convertToModel(respond);
    }

}

