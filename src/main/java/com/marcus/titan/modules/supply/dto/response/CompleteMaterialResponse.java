package com.marcus.titan.modules.supply.dto.response;

import com.marcus.titan.modules.supply.enums.SupplyStatus;

import java.time.Instant;

public record CompleteMaterialResponse(
        Long id,
        String position,
        String su,
        String sku,
        Double quantity,
        SupplyStatus status,
        String createdBy,
        Instant createdAt,
        TrackingResponse tracking
) {
}
