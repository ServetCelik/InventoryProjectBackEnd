package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Product;

public interface UpdateProductUseCase {
    Product updateProduct(Product request);
}
