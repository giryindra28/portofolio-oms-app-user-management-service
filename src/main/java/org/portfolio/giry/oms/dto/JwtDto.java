package org.portfolio.giry.oms.dto;

import lombok.Data;

@Data
public class JwtDto {
    private String username;
    private String email;
    private String roleName;
}
