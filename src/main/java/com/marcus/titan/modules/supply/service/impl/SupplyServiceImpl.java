package com.marcus.titan.modules.supply.service.impl;

import com.marcus.titan.exceptions.ErrorInPickingMaterial;
import com.marcus.titan.exceptions.ModuleMisMatch;
import com.marcus.titan.modules.supply.finder.SupplyFinder;
import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.dto.request.*;
import com.marcus.titan.modules.supply.dto.response.CompleteMaterialResponse;
import com.marcus.titan.modules.supply.dto.response.MaterialResponse;
import com.marcus.titan.modules.supply.dto.response.MaterialRefuelingResponse;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import com.marcus.titan.modules.supply.entity.Supply;
import com.marcus.titan.modules.supply.entity.SupplyTracking;
import com.marcus.titan.modules.supply.enums.SupplyStatus;
import com.marcus.titan.modules.supply.mapper.SupplyMapper;
import com.marcus.titan.modules.supply.messaging.producer.SapProducer;
import com.marcus.titan.modules.supply.repository.SupplyRepository;
import com.marcus.titan.modules.supply.service.SupplyService;
import com.marcus.titan.modules.user.finder.UserFinder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class SupplyServiceImpl implements SupplyService {
    private final SupplyFinder supplyFinder;
    private final SupplyRepository supplyRepository;
    private final SapProducer producer;
    private final SupplyMapper mapper;
    private final UserFinder userFinder;

    public SupplyServiceImpl(SupplyFinder supplyFinder, SupplyRepository supplyRepository, SapProducer producer, SupplyMapper mapper, UserFinder userFinder) {
        this.supplyFinder = supplyFinder;
        this.supplyRepository = supplyRepository;
        this.producer = producer;
        this.mapper = mapper;
        this.userFinder = userFinder;
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
        Supply materialEntity = supplyFinder.findEntityById(message.id());
        materialEntity.updateFromMaterialMessage(message);
        supplyRepository.save(materialEntity);

    }

    @Override
    public List<SapResponse> getMaterials(Pageable pageable) {
        return supplyRepository.findAll(pageable).stream().map(mapper::toSapResponse).toList();
    }

    @Override
    @Transactional
    public MaterialResponse claimNextMaterial(Integer operatorId) {
        Supply claimedSupply = supplyFinder.findClaimedByOperatorAndStatus(operatorId, SupplyStatus.CLAIMED);
        if (claimedSupply != null) {
            return mapper.toMaterialResponse(claimedSupply);
        }
        Supply pickedSupply = supplyFinder.findClaimedByOperatorAndStatus(operatorId, SupplyStatus.PICKING);
        if (pickedSupply != null) {
            return mapper.toMaterialResponse(pickedSupply);
        }

        Supply supply = supplyFinder.findFirstEntityAvailable(Instant.now().minus(15, ChronoUnit.MINUTES));
        if (supply == null) {
            return null;
        }

        supply.claim(operatorId);
        supplyRepository.save(supply);
        return mapper.toMaterialResponse(supply);
    }

    @Override
    public MaterialResponse pickingClaimedMaterial(Integer id, MaterialPickingRequest request) {
        Supply supply = supplyFinder.findClaimedByOperatorAndStatus(id, SupplyStatus.CLAIMED);
        if (supply == null || !Objects.equals(supply.getId(), request.materialId())) {
            throw new ErrorInPickingMaterial();
        }
        if (!request.suCollected().contains(supply.getSu())) {
            throw new ErrorInPickingMaterial();
        }
        supply.pick();
        supplyRepository.save(supply);
        return mapper.toMaterialResponse(supply);
    }

    @Override
    public MaterialResponse placeMaterialInBuffer(Integer operatorId, MaterialBufferRequest request) {
        if (!request.localization().equalsIgnoreCase("BUFFER")) {
            throw new ErrorInPickingMaterial();
        }
        Supply supply = supplyFinder.findClaimedByOperatorAndStatus(operatorId, SupplyStatus.PICKING);
        if (supply == null || !Objects.equals(supply.getId(), request.id())) {
            throw new ErrorInPickingMaterial();
        }
        supply.placeInBuffer();
        supplyRepository.save(supply);
        return mapper.toMaterialResponse(supply);
    }

    @Override
    public MaterialRefuelingResponse pickingMaterialForRefueling(Integer id, String su) {
        Supply supply = supplyFinder.findByRefueling(su.replace("00", ""));
        supply.pickRefueling(id);
        supplyRepository.save(supply);
        return mapper.toMaterialRefuelingResponse(supply);
    }

    @Override
    public void supplyMaterial(Integer operatorId, MaterialSupplyRequest request) {
        Supply supply = supplyFinder.findClaimedByOperatorAndStatus(operatorId, SupplyStatus.REFUELING_MODULE);
        if (supply == null || !Objects.equals(supply.getId(), request.id())) {
            throw new ErrorInPickingMaterial();
        }
        if (!Objects.equals(supply.getModule(), request.module())) {
            throw new ModuleMisMatch();
        }
        supply.supply();
        supplyRepository.save(supply);
    }

    @Override
    public CompleteMaterialResponse getSupply(Long id) {
        Supply supply = supplyFinder.findEntityById(id);
        String createByName = userFinder.findById(supply.getCreatedBy()).name();
        SupplyTracking tracking = supply.getMovements();

        List<Integer> ids = Stream.of(
                tracking.getPickedBy(),
                tracking.getSuppliedBy()
        )
        .filter(Objects::nonNull)
        .distinct()
        .toList();

        Map<Integer, String> userNames = userFinder.findUserNamesByIds(ids);

        return mapper.toCompleteResponse(supply, createByName, userNames);
    }

    @Override
    public Page<SapResponse> searchMaterial(SearchMaterialRequest request, Pageable pageable) {
        Instant start = request.startDate() == null
                ? null
                : request.startDate().atStartOfDay(ZoneOffset.UTC).toInstant();

        Instant end = request.endDate() == null
                ? null
                : request.endDate().plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        return supplyRepository.findBySearchText(
                request.searchText().trim(),
                request.status(),
                start,
                end,
                pageable
        ).map(mapper::toSapResponse);
    }


}
