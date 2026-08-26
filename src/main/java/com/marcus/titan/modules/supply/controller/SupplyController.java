package com.marcus.titan.modules.supply.controller;

import com.marcus.titan.infra.security.authenticated.AuthenticatedUser;
import com.marcus.titan.modules.supply.dto.request.*;
import com.marcus.titan.modules.supply.dto.response.CompleteMaterialResponse;
import com.marcus.titan.modules.supply.dto.response.MaterialResponse;
import com.marcus.titan.modules.supply.dto.response.MaterialRefuelingResponse;
import com.marcus.titan.modules.supply.dto.response.SapResponse;
import com.marcus.titan.modules.supply.enums.SupplyStatus;
import com.marcus.titan.modules.supply.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', OWNER')")
    @PostMapping
    public ResponseEntity<Void> requestMaterial(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody MaterialRequest request
    ) {
        supplyService.createMaterialRequest(user.id(), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<SapResponse>>  getMaterials(Pageable pageable) {
        return ResponseEntity.ok(supplyService.getMaterials(pageable));
    }

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', 'OWNER', 'OPERATOR')")
    @PostMapping("/claim")
    public ResponseEntity<MaterialResponse> claimMaterial(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok().body(supplyService.claimNextMaterial(user.id()));
    }

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', 'OWNER', 'OPERATOR')")
    @PostMapping("/picking")
    public ResponseEntity<MaterialResponse> pickMaterial(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody MaterialPickingRequest request
        ) {

        return ResponseEntity.ok().body(supplyService.pickingClaimedMaterial(user.id(), request));
    }

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', 'OWNER', 'OPERATOR')")
    @PostMapping("/place-in-buffer")
    public ResponseEntity<MaterialResponse> placeInBuffer(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody MaterialBufferRequest request
    ){
        return ResponseEntity.ok().body(supplyService.placeMaterialInBuffer(user.id(), request));
    }

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', 'OWNER', 'ASSISTANT')")
    @PostMapping("/pick-refueling/{su}")
    public ResponseEntity<MaterialRefuelingResponse> pickRefueling(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable String su
    ){
        return ResponseEntity.ok().body(supplyService.pickingMaterialForRefueling(user.id(), su));
    }

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', 'OWNER', 'ASSISTANT')")
    @PostMapping("/supply-material")
    public ResponseEntity<Void> supplyMaterial(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestBody MaterialSupplyRequest request
    ){
        supplyService.supplyMaterial(user.id(), request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', 'OWNER')")
    @GetMapping("/{id}")
    public ResponseEntity<CompleteMaterialResponse> getSupply(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok().body(supplyService.getSupply(id));
    }

    @PreAuthorize("hasAnyRole('CHECKER', 'ADMIN', 'OWNER')")
    @GetMapping("/search")
    public ResponseEntity<Page<SapResponse>> searchSupplyByText(
            @ModelAttribute SearchMaterialRequest request,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(supplyService.searchMaterial(request, pageable));
    }
}
