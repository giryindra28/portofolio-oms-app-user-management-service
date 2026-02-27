package org.portfolio.giry.oms.dto;

import java.time.LocalDateTime;

public record GetAllUserResp(
        Integer id,
        String username,
        String email,
        String createdBy

) {
}
