package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class NoticeCotroller {
    // 공지사항가져오기
    public ResponseEntity<ApiResponse> readNotice() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
