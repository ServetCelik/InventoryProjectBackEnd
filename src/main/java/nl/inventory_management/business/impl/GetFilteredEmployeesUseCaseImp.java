package nl.inventory_management.business.impl;

import lombok.AllArgsConstructor;
import nl.inventory_management.business.conventer.EmployeeConverter;
import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.business.interfaces.GetFilteredEmployeesUseCase;
import nl.inventory_management.repository.EmployeeRepository;
import nl.inventory_management.repository.entity.EmployeeEntity;
import nl.inventory_management.repository.entity.RoleEntityEnum;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetFilteredEmployeesUseCaseImp implements GetFilteredEmployeesUseCase {
    private final EmployeeRepository employeeRepository;



    @Transactional
    @Override
    public List<Employee> getFilteredEmployees(String filterName, String filterLastName, String filterEmail, String filterRole) {
        List<EmployeeEntity> entitieList = null;
        String blank = "blank";

        String name = filterName.equals(blank) ? "" : filterName;
        String lastName = filterLastName.equals(blank) ? "" : filterLastName;
        String email = filterEmail.equals(blank) ? "" : filterEmail;
        String role = filterRole.equals(blank) ? "" : filterRole;

        if (!name.isBlank() && !lastName.isBlank() && !email.isBlank() && !role.isBlank()){
            entitieList = employeeRepository.findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role(name, lastName, email, RoleEntityEnum.valueOf(role));

        } else if (!name.isBlank() && !lastName.isBlank() && !email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(name, lastName, email);

        }else if (!name.isBlank() && !lastName.isBlank() && email.isBlank() && !role.isBlank()) {
            entitieList = employeeRepository.findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndRoles_Role(name, lastName, RoleEntityEnum.valueOf(role));

        }else if (!name.isBlank() && !lastName.isBlank() && email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(name, lastName);

        }else if (!name.isBlank() && lastName.isBlank() && !email.isBlank() && !role.isBlank()) {
            entitieList = employeeRepository.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role(name, email, RoleEntityEnum.valueOf(role));

        }else if (!name.isBlank() && lastName.isBlank() && !email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(name, email);

        }else if (!name.isBlank() && lastName.isBlank() && email.isBlank() && !role.isBlank()) {
            entitieList = employeeRepository.findByNameContainingIgnoreCaseAndRoles_Role(name, RoleEntityEnum.valueOf(role));

        }else if (!name.isBlank() && lastName.isBlank() && email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findByNameContainingIgnoreCase(name);

        }else if (name.isBlank() && !lastName.isBlank() && !email.isBlank() && !role.isBlank()) {
            entitieList = employeeRepository.findByLastNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoles_Role(lastName, email, RoleEntityEnum.valueOf(role));


        }else if (name.isBlank() && !lastName.isBlank() && !email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findByLastNameContainingIgnoreCaseAndEmailContainingIgnoreCase(lastName, email);


        }else if (name.isBlank() && !lastName.isBlank() && email.isBlank() && !role.isBlank()) {
            entitieList = employeeRepository.findByLastNameContainingIgnoreCaseAndRoles_Role(lastName, RoleEntityEnum.valueOf(role));


        }else if (name.isBlank() && !lastName.isBlank() && email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findByLastNameContainingIgnoreCase(lastName);


        }else if (name.isBlank() && lastName.isBlank() && !email.isBlank() && !role.isBlank()) {
            entitieList = employeeRepository.findByEmailContainingIgnoreCaseAndRoles_Role(email, RoleEntityEnum.valueOf(role));


        }else if (name.isBlank() && lastName.isBlank() && !email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findByEmailContainingIgnoreCase(email);


        }else if (name.isBlank() && lastName.isBlank() && email.isBlank() && !role.isBlank()) {
            entitieList = employeeRepository.findByRoles_Role( RoleEntityEnum.valueOf(role));


        }else if (name.isBlank() && lastName.isBlank() && email.isBlank() && role.isBlank()) {
            entitieList = employeeRepository.findAll();
        }else{
            entitieList = new ArrayList<>();
        }

        return entitieList.stream()
                .map(EmployeeConverter::convertToModel).toList();
    }

}



