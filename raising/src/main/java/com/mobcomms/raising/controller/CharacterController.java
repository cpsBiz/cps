package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import com.mobcomms.common.api.ApiResponseList;
import com.mobcomms.raising.dto.CharacterDto;
import com.mobcomms.raising.dto.CharacterRegDto;
import com.mobcomms.raising.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 정보 API", description = "사용자 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class CharacterController {

    private final CharacterService characterService;

    // 캐릭터 리스트 가져오기
    @GetMapping("")
    @Operation(summary = "캐릭터정보", description = "현재 캐릭터정보를 불러오는 api")
    public ResponseEntity<ApiResponseList<CharacterDto>> readCharacter(
            @Parameter(description = "유저고유번호", required = true)
            @RequestParam long userSeq,
            @Parameter(description = "고객사코드", required = true)
            @RequestParam long companyCode
    ) {
        try {
            return new ResponseEntity<>(ApiResponseList.ok(characterService.readCharacter()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ApiResponseList.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 캐릭터 등록하기
    @PostMapping("")
    @Operation(summary = "캐릭터등록", description = "캐릭터를 추가하는 api ")
    public ResponseEntity<ApiResponse<CharacterDto>> createCharacter(
            @Parameter(description = "유저고유번호", required = true)
            @RequestParam long userSeq,
            @Parameter(description = "고객사코드", required = true)
            @RequestParam long companyCode,
            @RequestBody CharacterRegDto characterRegDto
    ) {
        try {
            return new ResponseEntity<>(
                    ApiResponse.ok(characterService.createCharacter(characterRegDto)), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ApiResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

