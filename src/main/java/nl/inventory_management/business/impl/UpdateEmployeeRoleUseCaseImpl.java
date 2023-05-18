package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.exception.EmailAlreadyExistsException;
import nl.inventory_management.business.interfaces.UpdateEmployeeRoleUseCase;
import nl.inventory_management.business.conventer.EmployeeConverter;
import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.business.exception.EmployeeNotFoundException;
import nl.inventory_management.repository.EmployeeRepository;
import nl.inventory_management.repository.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateEmployeeRoleUseCaseImpl implements UpdateEmployeeRoleUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee updateRole(Employee request) {

        if (!employeeRepository.existsById(request.getId())){
            throw new EmployeeNotFoundException();
        }
        if (employeeRepository.existsByEmail(request.getEmail()) &&
            !employeeRepository.findByEmail(request.getEmail()).getId().equals(request.getId())){
                throw new EmailAlreadyExistsException();
        }

        return save(request);
    }
    private Employee save(Employee employee){
        EmployeeEntity entity = EmployeeConverter.convertToEntity(employee);
        EmployeeEntity respond = employeeRepository.save(entity);
        return EmployeeConverter.convertToModel(respond);
    }

}

