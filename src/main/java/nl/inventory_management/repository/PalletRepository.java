package nl.inventory_management.repository;

import nl.inventory_management.repository.entity.LocationEntity;
import nl.inventory_management.repository.entity.PalletEntity;
import nl.inventory_management.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface PalletRepository extends JpaRepository<PalletEntity,Long> {
    boolean existsByLocationEntity(Optional<LocationEntity> locationEntity);
    boolean existsByProductEntity(Optional<ProductEntity> productEntity);
    @Query(value = "SELECT sum(amount) FROM test.pallet WHERE product_id = ?1", nativeQuery = true)
    int findTotalAmountByProduct(Long id);
    @Query(value = "SELECT sum(amount) FROM test.pallet WHERE location_id = ?1", nativeQuery = true)
    int findTotalAmountByLocation(Long id);
}
