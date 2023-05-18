package nl.inventory_management.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "location")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 2, max = 30)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotBlank
    @Length(min = 2, max = 30)
    @Column(name = "address", nullable = false, unique = true)
    private String address;


    @Column(name = "max_pallet")
    @Max(1000)
    private int maxPallet;


//    @OneToMany(fetch=FetchType.LAZY,
//            mappedBy = "locationEntity",
//            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<PalletEntity> palletEntities;
}
