package com.cps.rewardDotPitch.controller;

import com.cps.agencyService.packet.CpsRewardDotPitchPacket;
import com.cps.agencyService.service.CpsRewardDotPitchService;
import com.cps.common.constant.Constant;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RewardController {

    private final CpsRewardDotPitchService cpsRewardDotPitchService;

    /**
     * 도트 실시간 API
     *
     * @date 2024-09-20
     */
    @Operation(summary = "도트 실시간 API", description = "")
    @PostMapping(value = "/realTime", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<CpsRewardDotPitchPacket.RewardInfo.RewardResponse> realTime(@RequestParam Map<String, Object> params) throws Exception {
        //json 형식이 아니여서 MAP으로 받음
        var result = new CpsRewardDotPitchPacket.RewardInfo.RewardResponse();
        var request = new CpsRewardDotPitchPacket.RewardInfo.RealTimeRequest();

        try {
            request.setR_Keyid(getStringParam(params, "R_Keyid"));
            request.setR_Ordid(getStringParam(params, "R_Ordid"));
            request.setR_Date(getStringParam(params, "R_Date"));
            request.setR_Gubun(getStringParam(params, "R_Gubun"));
            request.setR_Mid(getStringParam(params, "R_Mid"));
            request.setR_Aid(getStringParam(params, "R_Aid"));
            request.setR_ProdNm(getStringParam(params, "R_ProdNm"));
            request.setR_Quantity(getIntParam(params, "R_Quantity", 0)); // 기본값 0
            request.setR_OrdPrice(getIntParam(params, "R_OrdPrice", 0)); // 기본값 0
            request.setR_CommPrice(getIntParam(params, "R_CommPrice", 0)); // 기본값 0
            request.setR_CommRate(getFloatParam(params, "R_CommRate", 0.0f)); // 기본값 0.0f
            var reward = cpsRewardDotPitchService.realTime(request);

            if (Constant.RESULT_CODE_SUCCESS.equals(reward.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(reward.getResultCode(), reward.getResultMessage());
            }
            result.setData(reward.getData());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 문자열 파라미터를 가져오는 메서드
    private String getStringParam(Map<String, Object> params, String key) {
        Object value = params.get(key);
        return value != null ? String.valueOf(value) : null; // null일 경우 null 반환
    }

    // 정수 파라미터를 가져오는 메서드
    private int getIntParam(Map<String, Object> params, String key, int defaultValue) {
        Object value = params.get(key);
        if (value != null) {
            try {
                return Integer.parseInt(String.valueOf(value));
            } catch (NumberFormatException e) {
                // 숫자 형 변환 실패 시 기본값 반환
            }
        }
        return defaultValue; // null이거나 형 변환 실패 시 기본값 반환
    }

    // 부동 소수점 파라미터를 가져오는 메서드
    private float getFloatParam(Map<String, Object> params, String key, float defaultValue) {
        Object value = params.get(key);
        if (value != null) {
            try {
                return Float.parseFloat(String.valueOf(value));
            } catch (NumberFormatException e) {
                // 숫자 형 변환 실패 시 기본값 반환
            }
        }
        return defaultValue; // null이거나 형 변환 실패 시 기본값 반환
    }
}
