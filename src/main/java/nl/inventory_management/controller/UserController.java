package nl.inventory_management.controller;

import lombok.RequiredArgsConstructor;
import nl.inventory_management.business.interfaces.CreateUserUseCase;
import nl.inventory_management.business.interfaces.DeleteUserUseCase;
import nl.inventory_management.business.interfaces.GetUserUseCase;
import nl.inventory_management.business.interfaces.LoginUserUseCase;
import nl.inventory_management.business.domain.*;
import nl.inventory_management.business.domain.Employee;
import nl.inventory_management.controller.converter.UserConverter;
import nl.inventory_management.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping("/save")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        Employee employee = Employee.builder()
                .name(createUserRequest.getName())
                .lastName(createUserRequest.getLastName())
                .email(createUserRequest.getEmail())
                .build();

        employee.setRoles(Set.of(
                EmployeeRole.builder()
                        .employee(employee)
                        .role(RoleEnum.NONE)
                        .build()));


        User user = User.builder()
                .userName(createUserRequest.getUserName())
                .password(createUserRequest.getPassword())
                .employee(employee)
                .build();

        User responseEntity = createUserUseCase.createUser(user);
        CreateUserResponse response = UserConverter.userToCreateUserResponse(responseEntity);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> canLogin(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        String responseString = loginUserUseCase.loginUser(loginUserRequest.getName(),loginUserRequest.getPassword());
        User loggedUser = getUserUseCase.getUserByName(loginUserRequest.getName());
        LoginUserResponse response = LoginUserResponse.builder()
                .accessToken(responseString)
                .userName(loggedUser.getUserName())
                .roles((loggedUser.getEmployee().getRoles().stream().map(EmployeeRole::getRole).toList().toString()))
                .build();

        return ResponseEntity.ok(response);
    }
//    @GetMapping("/")
//    public ResponseEntity<List<GetUserResponse>> getAllUsers(){
//
//        List<User> responseEntityList = getUserUseCase.getAllUsers();
//        List<GetUserResponse> response = responseEntityList
//                .stream().map(UserConverter::userToGetUserResponse).toList();
//
//        return ResponseEntity.ok(response);
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long id) {
//
//        User responseEntity = getUserUseCase.getUserById(id);
//        GetUserResponse response = UserConverter.userToGetUserResponse(responseEntity);
//        return ResponseEntity.ok(response);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable Long id) {
//        DeleteUserResponse response = deleteUserUseCase.deleteUser(id);
//
//        return ResponseEntity.ok(response);
//    }

}
