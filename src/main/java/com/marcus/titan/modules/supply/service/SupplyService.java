package com.marcus.titan.modules.supply.service;

import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.dto.request.MaterialRequest;

public interface SupplyService {
    public void createMaterialRequest(Integer userId, MaterialRequest request);

    void processMaterialResponse(MaterialResponseMessage message);
}
