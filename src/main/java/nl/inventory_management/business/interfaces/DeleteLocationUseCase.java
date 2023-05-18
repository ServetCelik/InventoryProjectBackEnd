package nl.inventory_management.business.interfaces;

import nl.inventory_management.controller.dto.DeleteResponse;

public interface DeleteLocationUseCase {
    DeleteResponse deleteLocation(Long id);

}
