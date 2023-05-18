package nl.inventory_management.controller;

import lombok.RequiredArgsConstructor;
import nl.inventory_management.business.interfaces.CreateDepartmentUseCase;
import nl.inventory_management.business.interfaces.DeleteDepartmentUseCase;
import nl.inventory_management.business.interfaces.GetDepartmentUseCase;
import nl.inventory_management.business.interfaces.UpdateDepartmentUseCase;
import nl.inventory_management.business.domain.Department;
import nl.inventory_management.configuration.security.isauthenticated.IsAuthenticated;
import nl.inventory_management.controller.converter.DepartmentConverter;
import nl.inventory_management.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class DepartmentController {
    private final CreateDepartmentUseCase createDepartmentUseCase;
    private final GetDepartmentUseCase getDepartmentUseCase;
    private  final DeleteDepartmentUseCase deleteDepartmentUseCase;
    private  final UpdateDepartmentUseCase updateDepartmentUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping("/save")
    public ResponseEntity<CreateDepartmentResponse> createDepartment(@RequestBody @Valid CreateDepartmentRequest createDepartmentRequest) {
        Department department = Department.builder()
                .name(createDepartmentRequest.getName())
                .description(createDepartmentRequest.getDescription())
                .build();

        Department responseDepartment = createDepartmentUseCase.createDepartment(department);
        CreateDepartmentResponse response = DepartmentConverter.departmentToCreateDepartmentResponse(responseDepartment);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<CreateDepartmentResponse> updateDepartment(@PathVariable("id") long id
            ,@RequestBody @Valid UpdateDepartmentRequest updateDepartmentRequest) {
        Department department = Department.builder()
                .id(id)
                .name(updateDepartmentRequest.getName())
                .description(updateDepartmentRequest.getDescription())
                .build();

        Department responseDepartment = updateDepartmentUseCase.updateDepartment(department);
        CreateDepartmentResponse response = DepartmentConverter.departmentToCreateDepartmentResponse(responseDepartment);

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/")
    public ResponseEntity<List<GetDepartmentResponse>> getAllDepartments(){

        List<Department> responseEntityList = getDepartmentUseCase.getAllDepartments();
        List<GetDepartmentResponse> response = responseEntityList.stream()
                .map(DepartmentConverter::departmentToGetDepartmentResponse).toList();
        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<GetDepartmentResponse> getDepartment(@PathVariable Long id) {

        Department responseEntity = getDepartmentUseCase.getDepartmentById(id);
        GetDepartmentResponse response = DepartmentConverter.departmentToGetDepartmentResponse(responseEntity);
        return ResponseEntity.ok(response);
    }
//    @IsAuthenticated
//    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteDepartment(@PathVariable Long id) {
        DeleteResponse response = deleteDepartmentUseCase.deleteDepartment(id);

        return ResponseEntity.ok(response);
    }

}
