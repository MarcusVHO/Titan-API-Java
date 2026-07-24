package com.marcus.titan.modules.supply.mapper;

import com.marcus.titan.modules.supply.dto.message.MaterialRequestMessage;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import com.marcus.titan.modules.supply.entity.Supply;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SupplyMapper {
    SapResponse toSapResponse(Supply supply);

    MaterialRequestMessage toMaterialRequestMessage(Supply supply);
}
