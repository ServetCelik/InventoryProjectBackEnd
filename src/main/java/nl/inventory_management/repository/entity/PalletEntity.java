package nl.inventory_management.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pallet")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PalletEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "amount")
    private int amount;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER,
            cascade= { CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
//    CascadeType.PERSIST <-- When I add this cascade type i get an PersistentObjectException
    @JoinColumn(name = "location_id")
    private LocationEntity locationEntity;


    @OneToOne(fetch=FetchType.EAGER,
            cascade= {CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="product_id")
    private ProductEntity productEntity;














//    Regulations for approve Euro pallets
//    Euro pallets are manufactured according to strict quality regulation and are occasionally inspected by independent firms. All pallets should meet certain criteria before being approved.
//
//    The absence of an approval logo indicates a counterfeit pallet, which can cause damage to the product transported or handled by it.
//    Transporting products on an unauthentic pallet can lead to refusal of products by customers or sometimes serious penalties from authorities. Other requirements include:
//
//    Materials:  11 tables in high-quality wood, 9 solid woodblock, and 78 nails.
//    Euro pallet Dimensions: Length 1200 mmm (tolerance limit + 8/-5); Width 800mm (tolerance limit + 8/- 5x mm); Height 144mm (tolerance limit + 10/ - 0x mm)
//    Max Height: 2.2 meter
//    Weight: approximately 25kg.
//    Workload: 1500kg with a constant load on the entire pallet.
}
