package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.DeleteProductUseCase;
import nl.inventory_management.business.exception.ProductExistsInSomePalletsException;
import nl.inventory_management.business.exception.ProductNotFoundException;
import nl.inventory_management.controller.dto.DeleteResponse;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;
    private final PalletRepository palletRepository;
    @Transactional
    @Override
    public DeleteResponse deleteProduct(Long id) {
        if (!productRepository.existsById(id)){
            throw  new ProductNotFoundException();
        }
        boolean isExists = palletRepository.existsByProductEntity(productRepository.findById(id));
        if(isExists){
            throw new ProductExistsInSomePalletsException();
        }
        delete(id);

        return DeleteResponse.builder()
                .result("Product with id:" + id + "is deleted...")
                .build();
    }

    private void delete(Long id){
         productRepository.deleteById(id);
    }
}
