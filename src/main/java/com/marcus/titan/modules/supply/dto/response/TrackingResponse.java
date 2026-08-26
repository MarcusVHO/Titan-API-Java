package com.marcus.titan.modules.supply.dto.response;

import java.time.Instant;

public record TrackingResponse(
        Long id,
        Instant solicitedAt,
        String  pickedBy,
        Instant claimedAt,
        Instant pickedAt,
        Instant bufferAt,
        Instant getSupplyAt,
        Instant suppliedAt,
        String  suppliedBy
) {
}
