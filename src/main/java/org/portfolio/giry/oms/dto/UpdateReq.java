package org.portfolio.giry.oms.dto;

import java.util.Optional;

public record UpdateReq(
        Integer id,
        Optional<String> username,
        Optional<String> email

) {
}
