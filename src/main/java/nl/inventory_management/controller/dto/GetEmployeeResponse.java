package nl.inventory_management.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetEmployeeResponse {
    private long id;
    private String name;
    private String lastName;
    private String email;
    private Set<String> roles;

}
