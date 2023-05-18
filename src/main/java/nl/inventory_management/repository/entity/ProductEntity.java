package nl.inventory_management.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Length(min = 2, max = 50)
    @Column(name = "name")
    private String name;

    @Length(min = 2, max = 500)
    @Column(name = "description")
    private String description;

    @Column(name = "weight")
    private double weight;

    @Column(name = "width")
    private double width;

    @Column(name = "length")
    private double length;

    @Column(name = "height")
    private double height;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER,
                cascade= { CascadeType.MERGE,
                        CascadeType.DETACH, CascadeType.REFRESH})
////    @ManyToOne(fetch=FetchType.EAGER,
////            cascade= {CascadeType.ALL})
    @JoinColumn(name = "department_id")
    private DepartmentEntity departmentEntity;
}
