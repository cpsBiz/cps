package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class CharacterController {
    // 캐릭터 리스트 가져오기
    public ResponseEntity readCharacter() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

    // 캐릭터 등록하기
    public ResponseEntity createCharacter() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
}

