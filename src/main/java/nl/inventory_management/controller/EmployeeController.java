package nl.inventory_management.controller;

import lombok.RequiredArgsConstructor;
import nl.inventory_management.business.interfaces.GetEmployeeUseCase;
import nl.inventory_management.business.interfaces.GetFilteredEmployeesUseCase;
import nl.inventory_management.business.interfaces.UpdateEmployeeRoleUseCase;
import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.business.domain.EmployeeRole;
import nl.inventory_management.business.domain.RoleEnum;
import nl.inventory_management.configuration.security.isauthenticated.IsAuthenticated;
import nl.inventory_management.controller.converter.EmployeeConverter;
import nl.inventory_management.controller.dto.GetEmployeeResponse;
import nl.inventory_management.controller.dto.UpdateRoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class EmployeeController {
    private final GetEmployeeUseCase getEmployeeUseCase;
    private final UpdateEmployeeRoleUseCase updateEmployeeRoleUseCase;
    private final GetFilteredEmployeesUseCase getFilteredEmployeesUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_HR"})
    @GetMapping("/")
    public ResponseEntity<List<GetEmployeeResponse>> getAllEmployees() {

        List<Employee> responseEntityList = getEmployeeUseCase.getAllEmployees();
        List<GetEmployeeResponse> response = responseEntityList
                .stream().map(EmployeeConverter::employeeToGetEmployeeResponse).toList();

        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_HR"})
    @GetMapping("/filter/{name}/{lastName}/{email}/{role}")
    public ResponseEntity<List<GetEmployeeResponse>> getFilteredEmployees(@PathVariable String name, @PathVariable String lastName, @PathVariable String email, @PathVariable String role) {

        List<Employee> responseEntityList = getFilteredEmployeesUseCase.getFilteredEmployees(name,lastName,email,role);
        List<GetEmployeeResponse> response = responseEntityList
                .stream().map(EmployeeConverter::employeeToGetEmployeeResponse).toList();

        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_HR"})
    @GetMapping("/{id}")
    public ResponseEntity<GetEmployeeResponse> getEmployeeById(@PathVariable Long id) {

        Employee responseEntity = getEmployeeUseCase.getEmployeeById(id);
        GetEmployeeResponse response = EmployeeConverter.employeeToGetEmployeeResponse(responseEntity);
        return ResponseEntity.ok(response);
    }


    @IsAuthenticated
    @RolesAllowed({"ROLE_HR"})
    @PutMapping("/{id}")
    public ResponseEntity<GetEmployeeResponse> updateEmployeeRole(@PathVariable("id") long id,@RequestBody @Valid UpdateRoleRequest request) {

        Employee responseEntity = getEmployeeUseCase.getEmployeeById(id);
        responseEntity.setRoles(request.getRoles().stream().map(x -> EmployeeRole.builder().role(RoleEnum.valueOf(x)).build()).collect(Collectors.toSet()));
        Employee employee = updateEmployeeRoleUseCase.updateRole(responseEntity);
        GetEmployeeResponse response = EmployeeConverter.employeeToGetEmployeeResponse(employee);
        return ResponseEntity.ok(response);
    }
}