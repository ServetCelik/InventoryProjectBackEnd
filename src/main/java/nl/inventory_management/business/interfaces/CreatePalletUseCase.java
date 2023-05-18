package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Pallet;

public interface CreatePalletUseCase {
    Pallet createPallet(Pallet request);
}
