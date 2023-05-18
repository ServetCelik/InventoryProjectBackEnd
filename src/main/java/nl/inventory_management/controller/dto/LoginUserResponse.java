package nl.inventory_management.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponse {

    private String accessToken;
    private String userName;
    private String roles;

}
