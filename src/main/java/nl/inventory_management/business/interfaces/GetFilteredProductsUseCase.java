package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Product;

import java.util.List;

public interface GetFilteredProductsUseCase {
    List<Product> getFilteredProducts(String name, String description, String department);
}
