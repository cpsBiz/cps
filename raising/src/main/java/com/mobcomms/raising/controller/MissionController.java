package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class MissionController {
    // 미션 목록
    public ResponseEntity<ApiResponse> readMission() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
    // 미션 상태
    public ResponseEntity<ApiResponse> readMissionState() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

    // 미션 수행 포인트 적립
    public ResponseEntity<ApiResponse> updateUserPoint() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

    // 미션 수행 경험치 적립
    public ResponseEntity<ApiResponse> updateUserExp() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

    // 기타 미션 수행 후 처리 
    
}
