package nl.inventory_management.controller.converter;

import nl.inventory_management.business.domain.Invoice;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.controller.dto.CreateLocationResponse;
import nl.inventory_management.controller.dto.GetInvoiceResponse;
import nl.inventory_management.controller.dto.GetLocationResponse;

public final class InvoiceConverter {
    private InvoiceConverter(){

    }
    public static GetInvoiceResponse InvoiceToGetInvoiceResponse(Invoice invoice) {
       return    GetInvoiceResponse.builder()
                .id(invoice.getId())
                .productId((invoice.getProductId()))
                .productName(invoice.getProductName())
                .locationName(invoice.getLocationName())
                .amount(invoice.getAmount())
                .date(invoice.getDate())
                .build();

    }
}
