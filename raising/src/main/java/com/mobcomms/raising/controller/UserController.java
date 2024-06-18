package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import com.mobcomms.raising.dto.UserResDto;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "사용자 정보 API", description = "사용자 정보 관련 API")
@RequestMapping("/api")
public class UserController {
    // 로그인
    @GetMapping("/sign-in")
    @Operation(summary = "로그인", description = "로그인시도")
    public ResponseEntity<ApiResponse<UserResDto>> signIn(
            @RequestParam String mediaUserKey,
            @RequestParam String companyCode,
            @RequestParam String adid,
            @RequestParam String platform
    ) {
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
