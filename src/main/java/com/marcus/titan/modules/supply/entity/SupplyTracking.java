package com.marcus.titan.modules.supply.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class SupplyTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supply_id")
    @JsonIgnore
    private Supply supply;

    private Instant solicitedAt;

    private Integer  pickedBy;
    private Instant claimedAt;
    private Instant pickedAt;
    private Instant bufferAt;

    private Instant getSupplyAt;
    private Instant suppliedAt;
    private Integer  suppliedBy;


    public void claim(Integer operatorId){
        this.pickedBy = operatorId;
        this.claimedAt = Instant.now();
    }

    public void startPicking(){
        this.pickedAt = Instant.now();
    }

    public void endPicking(){
        this.bufferAt = Instant.now();
    }

    public void refund() throws Exception {
        if (bufferAt == null && pickedAt == null){
            claimedAt = null;
        } else {
            throw new Exception();
        }
    }



}
