package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.exception.ProductNameAlreadyExistsException;
import nl.inventory_management.business.interfaces.UpdateProductUseCase;
import nl.inventory_management.business.conventer.ProductConverter;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.ProductNotFoundException;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.ProductEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public Product updateProduct(Product request) {

        if (!productRepository.existsById(request.getId())){
            throw new ProductNotFoundException();
        }
        if(productRepository.existsByName(request.getName()) &&
            !productRepository.findByName(request.getName()).getId().equals(request.getId())){
                throw new ProductNameAlreadyExistsException();
        }

       return save(request);
    }
    private Product save(Product product){
        ProductEntity entity = ProductConverter.convertToEntity(product);
        ProductEntity respond = productRepository.save(entity);
        return ProductConverter.convertToModel(respond);
    }

}

