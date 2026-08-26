package com.marcus.titan.modules.supply.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MaterialBufferRequest(
        @NotNull(message = "Id is necessary!") Long id,
        @NotBlank(message = "Localization is necessary!") String localization

) {
}
