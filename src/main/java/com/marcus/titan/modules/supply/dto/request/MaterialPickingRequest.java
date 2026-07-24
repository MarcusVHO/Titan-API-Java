package com.marcus.titan.modules.supply.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MaterialPickingRequest (
        @NotNull(message = "Material id is necessary!") Long materialId,
        @NotBlank(message = "Su is necessary!") String suCollected
){
}
