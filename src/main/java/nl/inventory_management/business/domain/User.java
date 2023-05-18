package nl.inventory_management.business.domain;

import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Long id;

    private String userName;

    private String password;

    private Employee employee;

}
