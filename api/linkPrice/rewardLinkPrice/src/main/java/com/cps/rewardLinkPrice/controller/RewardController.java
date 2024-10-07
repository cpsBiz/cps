package com.cps.rewardLinkPrice.controller;

import com.cps.rewardLinkPrice.packet.CpsRewardLinkPricePacket;
import com.cps.rewardLinkPrice.service.CpsRewardLinkPriceService;
import com.cps.common.constant.Constant;
import com.cps.rewardLinkPrice.service.CpsRewardLinkPriceService;
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

    private final CpsRewardLinkPriceService cpsRewardLinkPriceService;

    /**
     * 링크 실시간 API
     *
     * @date 2024-09-25
     */
    @Operation(summary = "링크 실시간 API", description = "")
    @PostMapping(value = "/realTime")
    public ResponseEntity<CpsRewardLinkPricePacket.RewardInfo.RewardResponse> realTime(@Valid @RequestBody CpsRewardLinkPricePacket.RewardInfo.RealTimeRequest request) throws Exception {
        var result = new CpsRewardLinkPricePacket.RewardInfo.RewardResponse();

        try {
            var click = cpsRewardLinkPriceService.realTime(request);

            if (Constant.RESULT_CODE_SUCCESS.equals(click.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(click.getResultCode(), click.getResultMessage());
            }
            result.setData(click.getData());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
