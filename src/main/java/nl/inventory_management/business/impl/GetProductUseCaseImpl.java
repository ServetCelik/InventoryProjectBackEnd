package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.ProductConverter;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.ProductNotFoundException;
import nl.inventory_management.business.interfaces.GetProductUseCase;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.ProductEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GetProductUseCaseImpl implements GetProductUseCase {
    private final ProductRepository productRepository;
    @Transactional
    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> entities  = productRepository.findAll();
        return entities.stream()
                .map(ProductConverter::convertToModel).toList();
    }
    @Transactional
    @Override
    public Product getProductById(Long id) {
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException();
        }
        ProductEntity entity = productRepository.findById(id).orElse(null);
        return ProductConverter.convertToModel(entity);
    }
    @Transactional
    @Override
    public Product getProductByName(String name) {
        if(!productRepository.existsByName(name)){
            throw new ProductNotFoundException();
        }
        ProductEntity entity = productRepository.findByName(name);
        return ProductConverter.convertToModel(entity);
    }
}
