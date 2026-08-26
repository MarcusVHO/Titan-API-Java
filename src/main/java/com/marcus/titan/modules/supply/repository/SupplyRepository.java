package com.marcus.titan.modules.supply.repository;

import com.marcus.titan.modules.supply.entity.Supply;
import com.marcus.titan.modules.supply.enums.SupplyStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    Optional<Supply> findByOperatorAndStatus(
            Integer operatorId,
            SupplyStatus status);


    Optional<Supply> findBySuAndStatus(String operatorId, SupplyStatus status);

    Optional<Supply> findBySu(String su);

    @Query("""
    SELECT s
    FROM Supply s
    WHERE (
        :searchText IS NULL
        OR :searchText = ''
        OR LOWER(s.sku) LIKE LOWER(CONCAT('%', :searchText, '%'))
        OR LOWER(s.su) LIKE LOWER(CONCAT('%', :searchText, '%'))
        OR LOWER(s.module) LIKE LOWER(CONCAT('%', :searchText, '%'))
        OR LOWER(s.position) LIKE LOWER(CONCAT('%', :searchText, '%'))
    )
    AND (:status IS NULL OR s.status = :status)
    AND (:startDate IS NULL OR s.createdAt >= :startDate)
    AND (:endDate IS NULL OR s.createdAt < :endDate)
""")
    Page<Supply> findBySearchText(
            @Param("searchText") String searchText,
            @Param("status") SupplyStatus status,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable
    );
}