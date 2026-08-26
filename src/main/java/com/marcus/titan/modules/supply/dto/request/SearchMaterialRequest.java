package com.marcus.titan.modules.supply.dto.request;

import com.marcus.titan.modules.supply.enums.SupplyStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;

public record SearchMaterialRequest (
        String searchText,
        SupplyStatus status,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate
){
}
