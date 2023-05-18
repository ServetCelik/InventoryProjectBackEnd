package nl.inventory_management.business.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    private Long id;

    private Long productId;

    private String productName;

    private String locationName;

    private Integer amount;

    private LocalDateTime date;
}
