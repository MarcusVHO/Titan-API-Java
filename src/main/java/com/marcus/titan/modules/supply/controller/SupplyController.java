package com.marcus.titan.modules.supply.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/supplay")
public class SupplyController {

    @PostMapping
    public ResponseEntity<Void> requestMaterial() {

        return ResponseEntity.ok().build();
    }
}
