package com.marcus.titan.modules.supply.mapper;

import com.marcus.titan.modules.supply.dto.message.MaterialRequestMessage;
import com.marcus.titan.modules.supply.dto.response.*;
import com.marcus.titan.modules.supply.entity.Supply;
import com.marcus.titan.modules.supply.entity.SupplyTracking;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SupplyMapper {
    SapResponse toSapResponse(Supply supply);
    MaterialRequestMessage toMaterialRequestMessage(Supply supply);
    MaterialResponse toMaterialResponse(Supply supply);
    MaterialRefuelingResponse toMaterialRefuelingResponse(Supply supply);

    @Mapping(target = "tracking", source = "supply.movements")
    @Mapping(target = "createdBy", source = "createByName")
    CompleteMaterialResponse toCompleteResponse(
            Supply supply,
            String createByName,
            @Context Map<Integer, String> userNames
    );

    @Mapping(
            target = "pickedBy",
            expression = "java(userNames.get(movement.getPickedBy()))"
    )
    @Mapping(
            target = "suppliedBy",
            expression = "java(userNames.get(movement.getSuppliedBy()))"
    )
    TrackingResponse toTrackingResponse(
            SupplyTracking movement,
            @Context Map<Integer, String> userNames
    );
}
