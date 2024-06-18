package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class GoodsController {
    // 상품 리스트 받아오기
    public ResponseEntity<ApiResponse> readGoods() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

}
