package com.mobcomms.raising.controller;

import com.mobcomms.common.api.ApiResponse;
import com.mobcomms.raising.dto.MissionExecuteDto;
import com.mobcomms.raising.dto.packet.MissionPacket.*;
import com.mobcomms.raising.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "미션 정보 API", description = "미션 정보 관련 API")
@RequestMapping("/mission")
public class MissionController {

    private final MissionService missionService;

    // 미션정보 호출
    @GetMapping("")
    @Operation(summary = "미션정보호출", description = "미션현황")
    public ResponseEntity<ReadMission.Response> readMission(
            @Parameter(description = "유저고유번호", required = true)
            @RequestParam long userSeq,
            @Parameter(description = "고객사코드", required = true)
            @RequestParam String companyCode
    ) {

        ReadMission.Response response = new ReadMission.Response();
        try {
            response.setSuccess();
            response.setData(missionService.readMission());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/execute")
    @Operation(summary = "미션수행", description = "미션수행정보 저장 api")
    public ResponseEntity<ReadMission.Response> createMissionExecute(
            @Parameter(description = "유저고유번호", required = true)
            @RequestParam long userSeq,
            @Parameter(description = "고객사코드", required = true)
            @RequestParam String companyCode,
            @Parameter(description = "미션수행정보", required = true)
            @RequestBody MissionExecuteDto missionExecuteDto)
    {
        ReadMission.Response response = new ReadMission.Response();
        try {
            response.setSuccess();
            response.setData(missionService.missionExecute(userSeq, missionExecuteDto));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
