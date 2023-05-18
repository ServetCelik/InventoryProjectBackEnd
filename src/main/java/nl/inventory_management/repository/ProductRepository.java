package nl.inventory_management.repository;

import nl.inventory_management.repository.entity.DepartmentEntity;
import nl.inventory_management.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    boolean existsByName(String name);
    ProductEntity findByName(String name);
    boolean existsByDepartmentEntity(Optional<DepartmentEntity> departmentEntity);
    List<ProductEntity> findByNameContaining(String name);

    List<ProductEntity> findByNameContainingAndDepartmentEntity_NameContaining(String name,String departmentEntity_Name);
    List<ProductEntity> findByNameContainingAndDescriptionContaining(String name,String description);
    List<ProductEntity> findByNameContainingAndDescriptionContainingAndDepartmentEntity_NameContaining(String name,String description,String department);
    List<ProductEntity> findByDescriptionContaining(String description);
    List<ProductEntity> findByDepartmentEntity_NameContaining(String department);
    List<ProductEntity> findByDescriptionContainingAndDepartmentEntity_NameContaining(String description,String department);




}
