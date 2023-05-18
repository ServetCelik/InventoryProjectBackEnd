package nl.inventory_management.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
//    @NotBlank
    private Long id;
    //@NotBlank
    private String name;
    //@NotBlank
    private String description;
    //@NotBlank
    private double weight;
    //@NotBlank
    private double width;
    //@NotBlank
    private double length;
    //@NotBlank
    private double height;
    //@NotBlank
    private String departmentName;
}
