package com.marcus.titan.modules.supply.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class SupplyMoving {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id")
    private Supply supply;

    private Instant solicitedAt;

    private Instant pickingAt;
    private Long  pickedBy;

    private Instant suppliedAt;
    private Long  suppliedBy;

}
