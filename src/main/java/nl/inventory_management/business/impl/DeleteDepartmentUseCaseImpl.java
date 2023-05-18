package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.interfaces.DeleteDepartmentUseCase;
import nl.inventory_management.business.exception.DepartmentHasSomeProductsException;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.controller.dto.DeleteResponse;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteDepartmentUseCaseImpl implements DeleteDepartmentUseCase {
    private final DepartmentRepository departmentRepository;
    private final ProductRepository productRepository;
    @Transactional
    @Override
    public DeleteResponse deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)){
            throw  new DepartmentNotFoundException();
        }
        Optional<DepartmentEntity> entity = departmentRepository.findById(id);
        boolean hasProducts = productRepository.existsByDepartmentEntity(entity);
        if (hasProducts){
            throw  new DepartmentHasSomeProductsException();
        }

        delete(id);

        return DeleteResponse.builder()
                .result("Department with id:" + id + "is deleted...")
                .build();
    }

    private void delete(Long id){
         departmentRepository.deleteById(id);
    }
}
