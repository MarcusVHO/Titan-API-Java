package com.marcus.titan.modules.supply.service;

import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.dto.request.MaterialRequest;
import com.marcus.titan.modules.supply.dto.response.MaterialClaimResponse;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplyService {
    void createMaterialRequest(Integer userId, MaterialRequest request);
    void processMaterialResponse(MaterialResponseMessage message);
    List<SapResponse> getMaterials(Pageable pageable);
    MaterialClaimResponse claimNextMaterial(Integer operatorId);

    void pickingClaimedMaterial(Integer id, @Valid MaterialRequest request);
}
