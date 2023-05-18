package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.EmployeeConverter;
import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.business.exception.EmployeeNotFoundException;
import nl.inventory_management.business.interfaces.GetEmployeeUseCase;
import nl.inventory_management.repository.EmployeeRepository;
import nl.inventory_management.repository.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GetEmployeeUseCaseImp implements GetEmployeeUseCase {
    private final EmployeeRepository employeeRepository;



    @Transactional
    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> entities = employeeRepository.findAll();
        return entities.stream()
                .map(EmployeeConverter::convertToModel).toList();
    }
    @Transactional
    @Override
    public Employee getEmployeeById(Long id) {
        if(!employeeRepository.existsById(id)){
            throw new EmployeeNotFoundException();
        }
        EmployeeEntity entity =  employeeRepository.findById(id).orElse(null);
        return EmployeeConverter.convertToModel(entity);

    }
    @Transactional
    @Override
    public Employee getEmployeeByName(String name) {
        if(!employeeRepository.existsByName(name)){
            throw new EmployeeNotFoundException();
        }
        EmployeeEntity entity =  employeeRepository.findByName(name);
        return EmployeeConverter.convertToModel(entity);
    }


}