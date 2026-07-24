package com.marcus.titan.modules.supply.controller;

import com.marcus.titan.infra.security.authenticated.AuthenticatedUser;
import com.marcus.titan.modules.supply.dto.request.MaterialRequest;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import com.marcus.titan.modules.supply.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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


    @GetMapping()
    public ResponseEntity<List<SapResponse>>  getMaterials(Pageable pageable) {
        return ResponseEntity.ok(supplyService.getMaterials(pageable));
    }

    @PostMapping("/claim")
    public ResponseEntity<Void> claimMaterial() {
        return ResponseEntity.ok().build();
    }
}
