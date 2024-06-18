package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class RouletteController {
    // 룰렛게임정보 받아오기
    public ResponseEntity<ApiResponse> readRoulette() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
