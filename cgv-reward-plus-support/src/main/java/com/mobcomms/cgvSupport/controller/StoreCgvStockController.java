package com.mobcomms.cgvSupport.controller;

import com.mobcomms.cgvSupport.dto.StoreCgvStockDto;
import com.mobcomms.cgvSupport.enums.StateEnum;
import com.mobcomms.cgvSupport.service.StoreCgvStockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreCgvStockController {

    private final StoreCgvStockService storeCgvStockService;

    @GetMapping("/coupon/state/{state}")
    public ResponseEntity<List<StoreCgvStockDto>> readAccumulationUserCount(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @PathVariable String state
    ) {
       return ResponseEntity.ok(
                storeCgvStockService.readStoreCgvStock(StateEnum.fromCode(state), startDate, endDate).stream()
                        .map(entity -> StoreCgvStockDto.of(entity))
                        .collect(Collectors.toList()));
    }

    @PatchMapping("/coupon/state")
    public ResponseEntity<List<StoreCgvStockDto>> updateCouponState(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return ResponseEntity.ok(
                Optional.ofNullable(storeCgvStockService.updateCouponState(startDate, endDate))
                        .orElse(Collections.emptyList())
                .stream()
                .map(entity -> StoreCgvStockDto.of(entity))
                .collect(Collectors.toList()));
    }
}
