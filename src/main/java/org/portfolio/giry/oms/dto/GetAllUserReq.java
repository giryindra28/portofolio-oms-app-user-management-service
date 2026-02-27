package org.portfolio.giry.oms.dto;

public record GetAllUserReq(
        Integer page,
        Integer size,
        String sortDirection,
        String sortField,
        String keyword
) {
}
