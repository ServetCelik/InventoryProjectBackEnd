package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.PalletConverter;
import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.business.exception.PalletNotFoundException;
import nl.inventory_management.business.interfaces.GetPalletUseCase;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.entity.PalletEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GetPalletUseCaseImpl implements GetPalletUseCase {
    private final PalletRepository palletRepository;
    @Transactional
    @Override
    public List<Pallet> getAllPallets() {
        List<PalletEntity> entities  = palletRepository.findAll();
        return entities.stream()
                .map(PalletConverter::convertToModel).toList();
    }
    @Transactional
    @Override
    public Pallet getPalletById(Long id) {
        if(!palletRepository.existsById(id)){
            throw new PalletNotFoundException();
        }
        PalletEntity entity = palletRepository.findById(id).orElse(null);
        return PalletConverter.convertToModel(entity);

    }

}
