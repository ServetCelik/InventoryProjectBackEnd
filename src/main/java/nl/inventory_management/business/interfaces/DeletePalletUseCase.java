package nl.inventory_management.business.interfaces;

import nl.inventory_management.controller.dto.DeleteResponse;

public interface DeletePalletUseCase {
    DeleteResponse deletePallet(Long id);

}
