package nl.inventory_management.business.domain;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRole {

    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;
}
