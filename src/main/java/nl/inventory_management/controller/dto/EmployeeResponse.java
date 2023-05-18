package nl.inventory_management.controller.dto;

import lombok.*;
import nl.inventory_management.business.domain.Employee;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponse {
    private List<Employee> employees;

}
