package nl.inventory_management.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employee")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Length(min = 2, max = 30)
    @Column(name = "name")
    private String name;

    @Length(min = 2, max = 30)
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Length(max = 50)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Set<EmployeeRoleEntity> roles;

//    @ElementCollection // 1
//    @CollectionTable(name = "role_list", joinColumns = @JoinColumn(name = "id")) // 2
//    @Column(name = "role") // 3
//    private List<String> roles;
//
//    @OneToOne(fetch=FetchType.LAZY,mappedBy="employeeEntity",cascade={CascadeType.ALL})
//    private UserEntity userEntity;
}
