package com.marcus.titan.modules.supply.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.marcus.titan.modules.supply.dto.message.MaterialResponseMessage;
import com.marcus.titan.modules.supply.enums.SupplyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Supply {

    public Supply(String sku, String module, Integer userId) {
        this.sku = sku;
        this.module = module;
        this.createdBy = userId;
        this.movements = new SupplyTracking();
        this.movements.setSupply(this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //su é identidade do palete
    private String su;

    //SKU codigo material
    @Column(length = 50, nullable = false)
    private String sku;

    @Column(length = 10, nullable = false)
    private String module;

    @Column(length = 12, name = "wh_pos")
    private String position;

    private Double quantity;

    private String batch;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SupplyStatus status = SupplyStatus.PENDING_REQUEST;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "supply")
    @JsonManagedReference
    private SupplyTracking movements;

    @CreationTimestamp
    private Instant createdAt;

    @Column(nullable = false)
    private Integer createdBy;




    public void updateFromMaterialMessage(MaterialResponseMessage message) {
        this.su = message.su();
        this.position = message.position();
        this.quantity = message.quantity();
        if (message.batch() != null ) {
            this.batch = message.batch();
        }
        this.movements.setSolicitedAt(Instant.now());
        this.status = SupplyStatus.REQUESTED;
    }

    public void claim (Integer operatorId) {
        this.status = SupplyStatus.CLAIMED;
        this.movements.claim(operatorId);
    }



}
