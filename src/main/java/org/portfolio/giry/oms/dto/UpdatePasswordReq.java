package org.portfolio.giry.oms.dto;

public record UpdatePasswordReq(
        String oldPassword,
        String newPassword
) {
}
