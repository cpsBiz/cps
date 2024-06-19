package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import com.mobcomms.common.api.ApiResponseList;
import com.mobcomms.raising.dto.CharacterRegDto;
import com.mobcomms.raising.dto.LoginResDto;

import com.mobcomms.raising.dto.UserCharacterDto;
import com.mobcomms.raising.dto.UserCharacterRegDto;
import com.mobcomms.raising.service.UserCharacterService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 정보 API", description = "사용자 정보 관련 API")
@RequestMapping("/user")
public class UserController {

    private final UserCharacterService userCharacterService;

    // 로그인
    @GetMapping("/sign-in")
    @Operation(summary = "로그인", description = "로그인시도")
    public ResponseEntity<ApiResponse<LoginResDto>> signIn(
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

    // 사용자캐릭터 목록 가져오기
    @GetMapping("/character")
    @Operation(summary = "캐릭터정보", description = "캐릭터정보 api")
    public ResponseEntity<ApiResponseList<UserCharacterDto>> readUserCharacter(
            @Parameter(description = "유저고유번호", required = true)
            @RequestParam long userSeq,
            @Parameter(description = "고객사코드", required = true)
            @RequestParam long companyCode
    ) {
        try {
          return ResponseEntity.ok(ApiResponseList.ok(userCharacterService.readUserCharacter(userSeq)));
        } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(ApiResponseList.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 사용자캐릭터 등록하기
    @PostMapping("/character")
    @Operation(summary = "유저 캐릭터 등록", description = "유저캐릭터정보 api")
    public ResponseEntity<ApiResponse<UserCharacterDto>> createUserCharacter(
            @Parameter(description = "유저고유번호", required = true)
            @RequestParam long userSeq,
            @Parameter(description = "고객사코드", required = true)
            @RequestParam String companyCode,
            @RequestBody UserCharacterRegDto userCharacterRegDto
    ){
        try {
            return new ResponseEntity<>(
                    ApiResponse.ok(userCharacterService.createUserCharacter(userSeq, userCharacterRegDto)),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ApiResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
