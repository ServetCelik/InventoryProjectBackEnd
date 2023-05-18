package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.CreateProductUseCase;
import nl.inventory_management.business.conventer.ProductConverter;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.ProductNameAlreadyExistsException;
import nl.inventory_management.business.exception.UnsupportedProductFieldsException;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.ProductEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {
    private final ProductRepository productRepository;
    @Transactional
    @Override
    public Product createProduct(Product request) {

        if (productRepository.existsByName(request.getName())){
            throw new ProductNameAlreadyExistsException();
        }
        if(request.getName().length()<2||request.getName().length()>50||
                request.getDescription().length()<2||request.getDescription().length()>500){
            throw new UnsupportedProductFieldsException();
        }

        return save(request);
    }

    private Product save(Product product){
        ProductEntity entity = ProductConverter.convertToEntity(product);
        ProductEntity respond = productRepository.save(entity);
        return ProductConverter.convertToModel(respond);

    }

}

