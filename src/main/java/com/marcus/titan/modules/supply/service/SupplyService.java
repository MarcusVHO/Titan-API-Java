package com.marcus.titan.modules.supply.service;

import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.dto.request.MaterialRequest;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplyService {
    void createMaterialRequest(Integer userId, MaterialRequest request);
    void processMaterialResponse(MaterialResponseMessage message);
    List<SapResponse> getMaterials(Pageable pageable);
}
