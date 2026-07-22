package com.marcus.titan.modules.supply.controller;


import com.marcus.titan.infra.security.authenticated.AuthenticatedUser;
import com.marcus.titan.modules.supply.dto.request.MaterialRequest;
import com.marcus.titan.modules.supply.service.SupplyService;
import com.marcus.titan.modules.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supply")
public class SupplyController {
    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }


    @PostMapping
    public ResponseEntity<Void> requestMaterial(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody MaterialRequest request
    ) {
        supplyService.createMaterialRequest(user.id(), request);
        return ResponseEntity.ok().build();
    }
}
