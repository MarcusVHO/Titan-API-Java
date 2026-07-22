package com.marcus.titan.modules.supply.entity;

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

    public Supply(Long su, Long sku, String module, SupplyStatus status, Long userId) {
        this.su = su;
        this.sku = sku;
        this.module = module;
        this.status = status;
        this.createdBy = userId;
        this.movements = new SupplyMoving();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //su é identidade do palete
    private Long su;

    //SKU codigo material
    private Long sku;

    private String module;

    private SupplyStatus status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "supply")
    private SupplyMoving movements;

    @CreationTimestamp
    private Instant createdAt;

    private Long createdBy;




}
