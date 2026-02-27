package org.portfolio.giry.oms.dto;

import java.util.List;

public record UsersAllResp(
        List<GetAllUserResp> users
) {
}
