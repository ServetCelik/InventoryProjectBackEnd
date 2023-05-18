package nl.inventory_management.repository;


import nl.inventory_management.repository.entity.InvoiceEntity;
import nl.inventory_management.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long> {


}
