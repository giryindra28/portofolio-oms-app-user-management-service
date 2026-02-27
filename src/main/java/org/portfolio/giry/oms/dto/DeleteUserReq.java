package org.portfolio.giry.oms.dto;

import java.util.List;

public record DeleteUserReq(
        List<Integer> ids
) {
}
