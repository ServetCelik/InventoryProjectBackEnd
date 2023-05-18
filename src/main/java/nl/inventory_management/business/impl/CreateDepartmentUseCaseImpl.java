package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.DepartmentConverter;
import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.exception.DepartmentAlreadyExistsException;
import nl.inventory_management.business.exception.DepartmentDescriptionTooShortException;
import nl.inventory_management.business.interfaces.CreateDepartmentUseCase;
import nl.inventory_management.repository.DepartmentRepository;
import nl.inventory_management.repository.entity.DepartmentEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateDepartmentUseCaseImpl implements CreateDepartmentUseCase {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new DepartmentAlreadyExistsException();
        } else if (request.getDescription().length() < 10) {
            throw new DepartmentDescriptionTooShortException();
        }

        return save(request);
    }

    private Department save(Department product) {
        DepartmentEntity entity = DepartmentConverter.convertToEntity(product);
        DepartmentEntity respond = departmentRepository.save(entity);
        return DepartmentConverter.convertToModel(respond);
    }

}
