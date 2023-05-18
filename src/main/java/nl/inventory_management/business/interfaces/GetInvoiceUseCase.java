package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Invoice;
import nl.inventory_management.business.domain.Location;

import java.util.List;

public interface GetInvoiceUseCase {
    List<Invoice> getAllInvoices();
    List<String> getStatisticByByProductId(Long id,String start,String end);
}
