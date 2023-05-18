package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.DepartmentConverter;
import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.exception.DepartmentAlreadyExistsException;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.business.interfaces.UpdateDepartmentUseCase;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateDepartmentUseCaseImpl implements UpdateDepartmentUseCase {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department updateDepartment(Department request) {

        if (!departmentRepository.existsById(request.getId())){
            throw new DepartmentNotFoundException();
        }
        if (departmentRepository.existsByName(request.getName()) &&
            !departmentRepository.findByName(request.getName()).getId().equals(request.getId())){
                throw new DepartmentAlreadyExistsException();
        }

        return save(request);
    }
    private Department save(Department department){
        DepartmentEntity entity = DepartmentConverter.convertToEntity(department);
        DepartmentEntity respond = departmentRepository.save(entity);
        return DepartmentConverter.convertToModel(respond);
    }

}

