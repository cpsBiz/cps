package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class AttendanceController {
    // 출석 미션정보 가져오기
    public ResponseEntity<ApiResponse> readAttendance() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

    // 사용자 출석현황 가져오기
    public ResponseEntity<ApiResponse> readUserAttendance() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
