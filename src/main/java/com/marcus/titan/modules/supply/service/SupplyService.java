package com.marcus.titan.modules.supply.service;

import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.dto.request.*;
import com.marcus.titan.modules.supply.dto.response.CompleteMaterialResponse;
import com.marcus.titan.modules.supply.dto.response.MaterialResponse;
import com.marcus.titan.modules.supply.dto.response.MaterialRefuelingResponse;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplyService {
    void createMaterialRequest(Integer userId, MaterialRequest request);
    void processMaterialResponse(MaterialResponseMessage message);
    List<SapResponse> getMaterials(Pageable pageable);
    MaterialResponse claimNextMaterial(Integer operatorId);

    MaterialResponse pickingClaimedMaterial(Integer id, MaterialPickingRequest request);

    MaterialResponse placeMaterialInBuffer(Integer operatorId, MaterialBufferRequest request);

    MaterialRefuelingResponse pickingMaterialForRefueling(Integer id, String su);

    void supplyMaterial(Integer operatorId, MaterialSupplyRequest request);
    CompleteMaterialResponse getSupply(Long id);

    Page<SapResponse> searchMaterial(SearchMaterialRequest request, Pageable pageable);
}
