package nl.inventory_management.business.interfaces;

import nl.inventory_management.controller.dto.DeleteResponse;

public interface DeleteProductUseCase {
    DeleteResponse deleteProduct(Long id);
}
