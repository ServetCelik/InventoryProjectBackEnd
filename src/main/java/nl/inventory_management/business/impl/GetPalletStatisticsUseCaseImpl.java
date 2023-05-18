package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.LocationConverter;
import nl.inventory_management.business.conventer.ProductConverter;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.interfaces.GetPalletStatisticsUseCase;
import nl.inventory_management.repository.LocationRepository;
import nl.inventory_management.repository.PalletRepository;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.LocationEntity;
import nl.inventory_management.repository.entity.ProductEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetPalletStatisticsUseCaseImpl implements GetPalletStatisticsUseCase {
    private final PalletRepository palletRepository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    @Transactional
    @Override
    public HashMap<Product, Integer> getTotalProductsByProduct() {

       return getAllProducts().stream().filter(x-> hasPalletWithProduct(x)).toList().stream()
                .collect(Collectors.toMap(x->x, x->palletRepository.findTotalAmountByProduct(x.getId())
                        , (prev, next) -> next, HashMap::new));
//
    }
    @Transactional
    @Override
    public HashMap<Location, Integer> getTotalProductsByLocation() {
        return getAllLocations().stream().filter(x-> hasPalletInLocation(x)).toList().stream()
                .collect(Collectors.toMap(x->x, x->palletRepository.findTotalAmountByLocation(x.getId())
                        , (prev, next) -> next, HashMap::new));

    }
    //This method checks the given product and return true if given product
    // exists in one of our locations
    private boolean hasPalletWithProduct(Product product){
        ProductEntity entity = ProductConverter.convertToEntity(product);
        return palletRepository.existsByProductEntity(Optional.ofNullable(entity));
    }
    //This method checks the given location and return true if given location
    // has some pallets in it
    private boolean hasPalletInLocation(Location location){
        LocationEntity entity = LocationConverter.convertToEntity(location);
        return palletRepository.existsByLocationEntity(Optional.ofNullable(entity));
    }
    private List<Product> getAllProducts() {
        List<ProductEntity> entities  = productRepository.findAll();
        return entities.stream()
                .map(ProductConverter::convertToModel).toList();
    }
    private List<Location> getAllLocations() {
        List<LocationEntity> entities = locationRepository.findAll();
        return entities.stream()
                .map(LocationConverter::convertToModel).toList();
    }

}
