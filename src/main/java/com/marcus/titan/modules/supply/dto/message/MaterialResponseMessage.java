package com.marcus.titan.modules.supply.dto.message;

public record MaterialResponseMessage(
        Long id,
        String position,
        String batch,
        String su,
        Double quantity,
        String module
) {
}
