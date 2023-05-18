package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.Invoice;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.repository.entity.InvoiceEntity;
import nl.inventory_management.repository.entity.LocationEntity;

public final class InvoiceConverter {
    private InvoiceConverter() {

    }

    public static Invoice convertToModel(InvoiceEntity invoice) {
        return Invoice.builder()
                .id(invoice.getId())
                .productId((invoice.getProductId()))
                .productName(invoice.getProductName())
                .locationName(invoice.getLocationName())
                .amount(invoice.getAmount())
                .date(invoice.getDate())
                .build();
    }

    public static InvoiceEntity convertToEntity(Invoice invoice) {
        return InvoiceEntity.builder()
                .id(invoice.getId())
                .productId((invoice.getProductId()))
                .productName(invoice.getProductName())
                .locationName(invoice.getLocationName())
                .amount(invoice.getAmount())
                .date(invoice.getDate())
                .build();
    }
}