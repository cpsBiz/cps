package com.cps.cpsApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsApi.packet.CpsRewardPacket;
import com.cps.cpsApi.service.CpsRewardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CpsRewardController {

    private final CpsRewardService cpsRewardService;

    /**
     * 도트피치 익일 호출
     *
     * @date 2024-09-23
     */
    @Operation(summary = "도트피치 익일 호출")
    @PostMapping(value = "/dotPitch/reward")
    public ResponseEntity<CpsRewardPacket.RewardInfo.DotPitchResponse> dotPitchReward(@Valid @RequestBody CpsRewardPacket.RewardInfo.DotPitchRequest request) throws Exception {
        var result = new CpsRewardPacket.RewardInfo.DotPitchResponse();

        try {
            var member = cpsRewardService.dotPitchReward(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setData(member.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("userSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 도트피치 확정, 취소내역 호출
     *
     * @date 2024-09-23
     */
    @Operation(summary = "도트피치 확정, 취소내역 호출 (6일)")
    @PostMapping(value = "/dotPitch/rewardMonth")
    public ResponseEntity<CpsRewardPacket.RewardInfo.DotPitchResponse> dotPitchRewardMonth(@Valid @RequestBody CpsRewardPacket.RewardInfo.DotPitchRequest request) throws Exception {
        var result = new CpsRewardPacket.RewardInfo.DotPitchResponse();
        LocalDate today = LocalDate.parse(request.getSearch_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate startDate = today.minusMonths(2).withDayOfMonth(6);
        LocalDate endDate = today.minusMonths(1).withDayOfMonth(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                List<String> depthList = Arrays.asList("c", "o");
                for (String depth : depthList) {
                    request.setSearch_type(depth);
                    request.setSearch_date(date.format(formatter));
                    var reward = cpsRewardService.dotPitchReward(request);
                    if (Constant.RESULT_CODE_SUCCESS.equals(reward.getResultCode())) {
                        result.setSuccess();
                    } else {
                        result.setApiMessage(reward.getResultCode(), reward.getResultMessage());
                    }
                    result.setData(reward.getData());
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("userSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 링크프라이스 확정, 취소내역 호출
     *
     * @date 2024-09-23
     */
    @Operation(summary = "링크프라이스 확정, 취소내역 호출 (6일)")
    @PostMapping(value = "/linkPrice/rewardMonth")
    public ResponseEntity<CpsRewardPacket.RewardInfo.DotPitchResponse> linkPriceRewardMonth(@Valid @RequestBody CpsRewardPacket.RewardInfo.LinkPriceRequest request) throws Exception {
        var result = new CpsRewardPacket.RewardInfo.DotPitchResponse();
        LocalDate today = LocalDate.parse(request.getYyyymmdd(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate startDate = today.minusMonths(2).withDayOfMonth(6);
        LocalDate endDate = today.minusMonths(1).withDayOfMonth(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                List<String> depthList = Arrays.asList("N", "Y");
                for (String depth : depthList) {
                    request.setCancel_flag(depth);
                    request.setYyyymmdd(date.format(formatter));
                    var reward = cpsRewardService.linkPriceReward(request);
                    if (Constant.RESULT_CODE_SUCCESS.equals(reward.getResultCode())) {
                        result.setSuccess();
                    } else {
                        result.setApiMessage(reward.getResultCode(), reward.getResultMessage());
                    }
                    result.setData(reward.getData());
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("userSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
