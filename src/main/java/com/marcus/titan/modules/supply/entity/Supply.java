package com.marcus.titan.modules.supply.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    public Supply(String su, String module, Integer userId) {
        this.su = su;
        this.module = module;
        this.status = SupplyStatus.PENDING_REQUEST;
        this.createdBy = userId;
        this.movements = new SupplyMoving();
        this.movements.setSupply(this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //su é identidade do palete
    private String su;

    //SKU codigo material
    private String sku;

    private String module;

    private SupplyStatus status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "supply")
    @JsonManagedReference
    private SupplyMoving movements;

    @CreationTimestamp
    private Instant createdAt;

    private Integer createdBy;




}
