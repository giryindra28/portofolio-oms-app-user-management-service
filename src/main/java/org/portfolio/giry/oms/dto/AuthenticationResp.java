package org.portfolio.giry.oms.dto;

public record AuthenticationResp(
        UserLoginResp user,
        String sessionId
) {
}
