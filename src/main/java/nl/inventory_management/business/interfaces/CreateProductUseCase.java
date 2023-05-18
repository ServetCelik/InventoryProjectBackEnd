package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Product;

public interface CreateProductUseCase {
    Product createProduct(Product request);

}
