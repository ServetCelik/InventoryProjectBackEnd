package nl.inventory_management.repository;

import nl.inventory_management.repository.entity.EmployeeEntity;
import nl.inventory_management.repository.entity.RoleEntityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    boolean existsByEmail(String email);
    EmployeeEntity findByEmail(String email);
    EmployeeEntity findByName(String name);
    Boolean existsByName(String name);
    List<EmployeeEntity> findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role(String name, String lastName, String email, RoleEntityEnum role);
    List<EmployeeEntity> findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String name, String lastName, String email);
    List<EmployeeEntity> findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndRoles_Role(String name, String lastName, RoleEntityEnum role);
    List<EmployeeEntity> findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String name, String lastName);
    List<EmployeeEntity> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role(String name, String email, RoleEntityEnum role);
    List<EmployeeEntity> findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String name, String email);

    List<EmployeeEntity> findByNameContainingIgnoreCaseAndRoles_Role(String name, RoleEntityEnum role);
    List<EmployeeEntity> findByNameContainingIgnoreCase(String name);


    List<EmployeeEntity> findByLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role(String lastName, String email, RoleEntityEnum role);
    List<EmployeeEntity> findByLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String lastName, String email);
    List<EmployeeEntity> findByLastNameContainingIgnoreCaseAndRoles_Role(String lastName, RoleEntityEnum role);
    List<EmployeeEntity> findByLastNameContainingIgnoreCase(String lastName);
    List<EmployeeEntity> findByEmailContainingIgnoreCaseAndRoles_Role(String email, RoleEntityEnum role);
    List<EmployeeEntity> findByEmailContainingIgnoreCase(String email);

    List<EmployeeEntity> findByRoles_Role( RoleEntityEnum role);


}

