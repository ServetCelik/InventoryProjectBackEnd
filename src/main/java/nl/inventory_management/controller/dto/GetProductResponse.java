package nl.inventory_management.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductResponse {

    private long id;
    private String name;
    private String description;
    private String departmentName;
    private double weight;
    private double width;
    private double length;
    private double height;


}
