package org.portfolio.giry.oms.dto;

public record UserLoginResp(
        Integer id,
        String username,
        String email,
        String roleName
) {
}
