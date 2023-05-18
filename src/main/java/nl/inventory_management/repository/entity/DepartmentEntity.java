package nl.inventory_management.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "department")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 2, max = 30)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank
    @Length(min = 10, max = 200)
    @Column(name = "description")
    private String description;


//    @OneToMany(fetch=FetchType.LAZY,
//            mappedBy = "departmentEntity",
//            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<ProductEntity> products;


}
