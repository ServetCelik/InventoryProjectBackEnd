package nl.inventory_management.repository;

import nl.inventory_management.repository.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {
    boolean existsByName(String name);
    DepartmentEntity findByName(String name);




}
