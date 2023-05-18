package nl.inventory_management.business.conventer;

import nl.inventory_management.business.domain.Product;
import nl.inventory_management.repository.entity.ProductEntity;

public final class ProductConverter {
    private ProductConverter(){

    }
    public static Product convertToModel(ProductEntity product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .weight(product.getWeight())
                .width(product.getWidth())
                .length(product.getLength())
                .height(product.getHeight())
                .department(DepartmentConverter.convertToModel(product.getDepartmentEntity()))
                .build();
    }
    public static ProductEntity convertToEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .weight(product.getWeight())
                .width(product.getWidth())
                .length(product.getLength())
                .height(product.getHeight())
                .departmentEntity(DepartmentConverter.convertToEntity(product.getDepartment()))
                .build();
    }
}
