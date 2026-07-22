package com.marcus.titan.modules.supply.repository;

import com.marcus.titan.modules.supply.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
