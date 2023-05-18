package nl.inventory_management.controller.converter;

import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.controller.dto.CreatePalletResponse;
import nl.inventory_management.controller.dto.GetPalletResponse;

public final class PalletConverter {
    private PalletConverter(){

    }
    public static GetPalletResponse palletToGetPalletResponse(Pallet pallet) {
        return GetPalletResponse.builder()
                .id(pallet.getId())
                .locationName(pallet.getLocation().getName())
                .productName(pallet.getProduct().getName())
                .amount(pallet.getAmount())
                .build();
    }
    public static CreatePalletResponse palletToCreatePalletResponse(Pallet pallet) {
        return CreatePalletResponse.builder()
                .id(pallet.getId())
                .build();
    }
}
