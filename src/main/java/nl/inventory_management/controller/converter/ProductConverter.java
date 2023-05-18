package nl.inventory_management.controller.converter;

import nl.inventory_management.business.domain.Product;
import nl.inventory_management.controller.dto.CreateProductResponse;
import nl.inventory_management.controller.dto.GetProductResponse;

public final class ProductConverter {
    private ProductConverter(){

    }
    public static GetProductResponse productToGetProductResponse(Product product) {
        return GetProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .weight(product.getWeight())
                .width(product.getWidth())
                .length(product.getLength())
                .height(product.getHeight())
                .departmentName(product.getDepartment().getName())
                .build();
    }
    public static CreateProductResponse productToCreateProductResponse(Product product) {
        return CreateProductResponse.builder()
                .id(product.getId())
                .build();
    }
}
