package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.ProductConverter;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.interfaces.GetFilteredProductsUseCase;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.ProductEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetFilteredProductsUseCaseImpl implements GetFilteredProductsUseCase {
    private final ProductRepository productRepository;
    @Transactional
    @Override
    public List<Product> getFilteredProducts(String filterName, String filterDescription, String filterDepartment) {

        String blank = "blank";
        List<ProductEntity> productList = null;
        String name = filterName.equals(blank) ? "" : filterName;
        String description = filterDescription.equals(blank) ? "" : filterDescription;
        String department = filterDepartment.equals(blank) ? "" : filterDepartment;

        if (name.isBlank()&&description.isBlank()&&department.isBlank()){
            productList  = productRepository.findAll();

        } else if (!name.isBlank() && description.isBlank() && department.isBlank()) {
            productList  = productRepository.findByNameContaining(name);

        } else if (!name.isBlank() && !description.isBlank() && department.isBlank()) {
            productList  = productRepository.findByNameContainingAndDescriptionContaining(name, description);

        }else if (!name.isBlank() && description.isBlank() && !department.isBlank()) {
            productList  = productRepository.findByNameContainingAndDepartmentEntity_NameContaining(name,department);

        }else if (!name.isBlank() && !description.isBlank() && !department.isBlank()) {
            productList  = productRepository.findByNameContainingAndDescriptionContainingAndDepartmentEntity_NameContaining(name,description,department);

        }else if (name.isBlank() && !description.isBlank() && department.isBlank()) {
            productList  = productRepository.findByDescriptionContaining(description);

        }else if (name.isBlank() &&description.isBlank() && !department.isBlank()) {
            productList  = productRepository.findByDepartmentEntity_NameContaining(department);

        }else if (name.isBlank() && !description.isBlank() && !department.isBlank()) {
            productList  = productRepository.findByDescriptionContainingAndDepartmentEntity_NameContaining(description,department);

        }else{
            productList = new ArrayList<>();
        }


        return productList.stream()
                .map(ProductConverter::convertToModel).toList();
    }

}
