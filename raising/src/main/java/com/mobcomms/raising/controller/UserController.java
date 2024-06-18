package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import org.springframework.http.ResponseEntity;

public class UserController {
    // 로그인
    public ResponseEntity<ApiResponse> signIn() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
    // 로그아웃
    public ResponseEntity<ApiResponse> signOut() {
        return ResponseEntity.ok(ApiResponse.ok());
    }

    // 가입
    public ResponseEntity<ApiResponse> signUp() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
    // 탈퇴
    public ResponseEntity<ApiResponse> withdrawal() {
         return ResponseEntity.ok(ApiResponse.ok());
     }
    // 사용자캐릭터 변경 api
    public ResponseEntity<ApiResponse> updateUserCharacter() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
    // 사용자 게임 상품 선택 저장하기
    public ResponseEntity<ApiResponse> updateUserGame() {
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
