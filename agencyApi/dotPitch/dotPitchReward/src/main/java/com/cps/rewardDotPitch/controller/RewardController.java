package com.cps.rewardDotPitch.controller;

import com.cps.agencyService.packet.CpsRewardDotPitchPacket;
import com.cps.agencyService.service.CpsRewardDotPitchService;
import com.cps.common.constant.Constant;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(value = "/realTime")
    public ResponseEntity<CpsRewardDotPitchPacket.RewardInfo.RewardResponse> realTime(@Valid @RequestBody CpsRewardDotPitchPacket.RewardInfo.RealTimeRequest request) throws Exception {
        var result = new CpsRewardDotPitchPacket.RewardInfo.RewardResponse();

        try {
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
}
