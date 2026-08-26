package com.marcus.titan.modules.supply.dto.request;

import jakarta.validation.constraints.NotNull;

public record MaterialSupplyRequest (
        @NotNull(message = "Id is necessary!") Long id,
        @NotNull(message = "Module is necessary!") String module
){
}
