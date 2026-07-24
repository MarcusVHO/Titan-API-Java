package com.marcus.titan.modules.supply.repository;

import com.marcus.titan.modules.supply.entity.Supply;
import com.marcus.titan.modules.supply.enums.SupplyStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT s
    FROM Supply s
    JOIN s.movements m
    WHERE
        s.status = 'REQUESTED'
        OR (
            s.status ='CLAIMED'
            AND m.claimedAt < :expirationTime
        )
        OR (
            s.status = 'PICKING'
            AND m.pickedAt < :expirationTime
        )
    ORDER BY s.createdAt
    LIMIT 1
""")
    Optional<Supply> findFirstAvailable(Instant expirationTime);

    @Query("""
        SELECT s
        FROM Supply s
        JOIN s.movements m
        WHERE m.pickedBy = :operatorId
          AND s.status = :status
    """)
    Optional<Supply> findClaimedByOperatorAndStatus(
            Integer operatorId,
            SupplyStatus status);

}
