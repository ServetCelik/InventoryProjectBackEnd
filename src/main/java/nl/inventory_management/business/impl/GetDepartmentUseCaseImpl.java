package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.DepartmentConverter;
import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.business.interfaces.GetDepartmentUseCase;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class GetDepartmentUseCaseImpl implements GetDepartmentUseCase {
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities.stream()
                .map(DepartmentConverter::convertToModel).toList();
    }
    @Override
    public Department getDepartmentById(Long id) {
        if(!departmentRepository.existsById(id)){
            throw new DepartmentNotFoundException();
        }
        DepartmentEntity entity = departmentRepository.findById(id).orElse(null);
        return DepartmentConverter.convertToModel(entity);
    }
    @Override
    public Department findDepartmentByName(String name) {
        if(!departmentRepository.existsByName(name)){
            throw new DepartmentNotFoundException();
        }
        DepartmentEntity entity = departmentRepository.findByName(name);
        return DepartmentConverter.convertToModel(entity);
    }
}