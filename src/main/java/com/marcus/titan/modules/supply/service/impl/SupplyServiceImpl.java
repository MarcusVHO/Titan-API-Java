package com.marcus.titan.modules.supply.service.impl;

import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.dto.request.MaterialRequest;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import com.marcus.titan.modules.supply.entity.Supply;
import com.marcus.titan.modules.supply.mapper.SupplyMapper;
import com.marcus.titan.modules.supply.messaging.producer.SapProducer;
import com.marcus.titan.modules.supply.repository.SupplyRepository;
import com.marcus.titan.modules.supply.service.SupplyService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository supplyRepository;
    private final SapProducer producer;
    private final SupplyMapper mapper;

    public SupplyServiceImpl(SupplyRepository supplyRepository, SapProducer producer, SupplyMapper mapper) {
        this.supplyRepository = supplyRepository;
        this.producer = producer;
        this.mapper = mapper;
    }

    @Override
    public void createMaterialRequest(Integer userId, MaterialRequest request) {
        Supply supply = new Supply(
                request.sku(),
                request.module(),
                userId
        );
        supplyRepository.save(supply);
        producer.sendMaterialRequest(mapper.toMaterialRequestMessage(supply));
    }

    @Override
    public void processMaterialResponse(MaterialResponseMessage message) {
        Supply materialEntity = supplyRepository.findById(message.id()).orElse(null);
        assert materialEntity != null;
        materialEntity.updateFromMaterialMessage(message);
        supplyRepository.save(materialEntity);

    }

    @Override
    public List<SapResponse> getMaterials(Pageable pageable) {
        return supplyRepository.findAll(pageable).stream().map(mapper::toSapResponse).toList();
    }


}
