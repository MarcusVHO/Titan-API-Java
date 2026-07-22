package com.marcus.titan.modules.supply.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MaterialRequest (
        @NotBlank String module,
        @NotNull String su
) {
}
