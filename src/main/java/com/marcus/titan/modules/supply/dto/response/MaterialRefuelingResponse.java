package com.marcus.titan.modules.supply.dto.response;

public record MaterialRefuelingResponse (
        Long id,
        String su,
        String sku,
        String module,
        Double quantity
) {
}
