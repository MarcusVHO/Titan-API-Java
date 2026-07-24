package com.marcus.titan.modules.supply.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class SupplyMoving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id")
    @JsonIgnore
    private Supply supply;

    private Instant solicitedAt;

    private Long  pickedBy;
    private Instant claimedAt;
    private Instant pickingAt;
    private Instant bufferAt;

    private Instant suppliedAt;
    private Long  suppliedBy;

}
