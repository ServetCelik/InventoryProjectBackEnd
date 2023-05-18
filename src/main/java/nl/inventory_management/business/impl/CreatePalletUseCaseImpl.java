package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.CreatePalletUseCase;
import nl.inventory_management.business.conventer.PalletConverter;
import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.entity.PalletEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreatePalletUseCaseImpl implements CreatePalletUseCase {
    private final PalletRepository palletRepository;

    @Transactional
    @Override
    public Pallet createPallet(Pallet request) {

        return save(request);
    }
    private Pallet save(Pallet pallet){
        PalletEntity entity = PalletConverter.convertToEntity(pallet);
        PalletEntity respond = palletRepository.save(entity);
        return PalletConverter.convertToModel(respond);

    }

}