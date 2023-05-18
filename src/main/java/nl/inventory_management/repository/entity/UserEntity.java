package nl.inventory_management.repository.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 2, max = 30)
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;


    @Column(name = "password")

    private String password;

    @OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="employee_id")
    private EmployeeEntity employeeEntity;

}
