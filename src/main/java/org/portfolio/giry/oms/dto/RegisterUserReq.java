package org.portfolio.giry.oms.dto;

import lombok.NoArgsConstructor;


public record RegisterUserReq(
        String username,
        String password,
        String email
) {
}
