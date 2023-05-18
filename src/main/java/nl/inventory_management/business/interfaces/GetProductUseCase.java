package nl.inventory_management.business.interfaces;

import nl.inventory_management.business.domain.Product;

import java.util.List;

public interface GetProductUseCase{
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product getProductByName(String name);
}
