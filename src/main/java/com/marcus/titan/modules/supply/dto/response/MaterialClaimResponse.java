package com.marcus.titan.modules.supply.dto.response;

public record MaterialClaimResponse(
        Long id,
        String position,
        String su,
        String sku,
        Double quantity

) {
}
