package nl.inventory_management.repository;

import nl.inventory_management.repository.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity,Long> {
    boolean existsByName(String name);
    LocationEntity findByName(String name);
}
