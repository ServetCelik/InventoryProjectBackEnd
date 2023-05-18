package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.DeletePalletUseCase;
import nl.inventory_management.business.exception.PalletNotFoundException;
import nl.inventory_management.controller.dto.DeleteResponse;
import nl.inventory_management.repository.InvoiceRepository;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.entity.InvoiceEntity;
import nl.inventory_management.repository.entity.PalletEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeletePalletUseCaseImpl implements DeletePalletUseCase {
    private final PalletRepository palletRepository;
    private final InvoiceRepository invoiceRepository;
    @Transactional
    @Override
    public DeleteResponse deletePallet(Long id) {
        if (!palletRepository.existsById(id)){
            throw  new PalletNotFoundException();
        }
        PalletEntity deletedPallet = palletRepository.findById(id).orElse(null);

        delete(id);

        invoiceRepository.save(
                InvoiceEntity.builder()
                        .productId(deletedPallet.getProductEntity().getId())
                        .productName(deletedPallet.getProductEntity().getName())
                        .locationName(deletedPallet.getLocationEntity().getName())
                        .amount(deletedPallet.getAmount())
                        .date(LocalDateTime.now())
                        .build()
        );

        return DeleteResponse.builder()
                .result("Pallet with id:" + id + "is deleted...")
                .build();
    }
    private void delete(Long id){
         palletRepository.deleteById(id);
    }
}
