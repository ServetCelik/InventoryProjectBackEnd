package nl.inventory_management.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePalletRequest {

    //@NotBlank
    private int amount;
    //@NotBlank
    private String LocationName;
    //@NotBlank
    private String productName;
}
