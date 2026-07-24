package com.marcus.titan.modules.supply.repository;

import com.marcus.titan.modules.supply.entity.SupplyTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyMovingRepository extends JpaRepository<SupplyTracking, Long> {
}
