package com.marcus.titan.modules.supply.finder;

import com.marcus.titan.exceptions.MaterialNotFound;
import com.marcus.titan.exceptions.SupplyNotFoundException;
import com.marcus.titan.modules.supply.entity.Supply;
import com.marcus.titan.modules.supply.enums.SupplyStatus;
import com.marcus.titan.modules.supply.repository.SupplyRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class SupplyFinder {
    private final SupplyRepository supplyRepository;

    public SupplyFinder(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public Supply findEntityById (Long id) {
        return supplyRepository.findById(id).orElseThrow(SupplyNotFoundException::new);
    }

    public Supply findClaimedByOperatorAndStatus(Integer operatorId, SupplyStatus status) {
        return supplyRepository.findByOperatorAndStatus(operatorId, status).orElse(null);
    }

    public Supply findFirstEntityAvailable(Instant time) {
        return supplyRepository.findFirstAvailable(time).orElse(null);
    }

    public Supply findByRefueling(String su) {
        Supply supply = supplyRepository.findBySuAndStatus(su, SupplyStatus.IN_BUFFER).orElse(null);
        if (supply == null) {
            supply = supplyRepository.findBySuAndStatus(su, SupplyStatus.REFUELING_MODULE).orElseThrow(MaterialNotFound::new);
        }
        return supply;
    }

    public Supply findEntityBySu(String su) {
        return supplyRepository.findBySu(su).orElseThrow(MaterialNotFound::new);
    }

}
