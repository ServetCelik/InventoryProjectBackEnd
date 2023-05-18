package nl.inventory_management.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalAmountOfProduct {
    private String product;
    private int amount;
}
