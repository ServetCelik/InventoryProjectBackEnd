package nl.inventory_management.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationRequest {

    //@NotBlank
    private String name;
    //@NotBlank
    private String address;
    //@NotBlank
    private int maxPallet;


}
