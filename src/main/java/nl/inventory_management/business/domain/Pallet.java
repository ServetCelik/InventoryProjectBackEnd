package nl.inventory_management.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pallet {
    private Long id;
    private int amount;
    private Location location;
    private Product product;


}
