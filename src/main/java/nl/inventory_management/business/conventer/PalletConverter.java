package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.Pallet;
import nl.inventory_management.repository.entity.PalletEntity;

public final class PalletConverter {
    private PalletConverter(){

    }
    public static Pallet convertToModel(PalletEntity pallet) {
        return Pallet.builder()
                .id(pallet.getId())
                .amount(pallet.getAmount())
                .location(LocationConverter.convertToModel(pallet.getLocationEntity()))
                .product(ProductConverter.convertToModel(pallet.getProductEntity()))
                .build();
    }
    public static PalletEntity convertToEntity(Pallet pallet) {
        return PalletEntity.builder()
                .id(pallet.getId())
                .amount(pallet.getAmount())
                .locationEntity(LocationConverter.convertToEntity(pallet.getLocation()))
                .productEntity(ProductConverter.convertToEntity(pallet.getProduct()))
                .build();
    }
}
