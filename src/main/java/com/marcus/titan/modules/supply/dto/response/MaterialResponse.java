package com.marcus.titan.modules.supply.dto.response;

import com.marcus.titan.modules.supply.enums.SupplyStatus;

public record MaterialResponse(
        Long id,
        String position,
        String su,
        String sku,
        Double quantity,
        SupplyStatus status

) {
}
