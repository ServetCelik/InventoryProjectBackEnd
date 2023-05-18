package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Pallet;

import java.util.List;

public interface GetPalletUseCase {
    List<Pallet> getAllPallets();
    Pallet getPalletById(Long id);
}
