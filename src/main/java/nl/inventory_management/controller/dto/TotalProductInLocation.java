package nl.inventory_management.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalProductInLocation {
    private String location;
    private int amount;
}
