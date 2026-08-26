package com.marcus.titan.modules.supply.dto.message;

public record MaterialRequestMessage(
        Long id,
        String sku,
        String module
) {
}
