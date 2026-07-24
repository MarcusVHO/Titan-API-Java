package com.marcus.titan.modules.supply.dto.response;

import com.marcus.titan.modules.supply.enums.SupplyStatus;

import java.time.Instant;

public record SapResponse (
        Long id,
        String su,
        String sku,
        String module,
        String position,
        SupplyStatus status,
        Instant createdAt
){
}
