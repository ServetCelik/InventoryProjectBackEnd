package nl.inventory_management.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInvoiceResponse {
    private Long id;

    private Long productId;

    private String productName;

    private String locationName;

    private Integer amount;

    private LocalDateTime date;

}