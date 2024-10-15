package com.cps.api.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.SummaryPacket;
import com.cps.cpsService.service.SummaryService;
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
public class SummaryController {

    private final SummaryService summaryService;

    /**
     * 리포트 조회
     *
     * @date 2024-09-19
     */
    @Operation(summary = "리포트 조회")
    @PostMapping(value = "/summaryCount")
    public ResponseEntity<SummaryPacket.SummaryInfo.SummaryResponse> summaryCount(@Valid @RequestBody SummaryPacket.SummaryInfo.SummaryRequest request) throws Exception {
        var result = new SummaryPacket.SummaryInfo.SummaryResponse();

        try {
            var member = summaryService.summaryCount(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess(member.getTotalCount(), member.getCnt(), member.getClickCnt(),member.getRewardCnt(), member.getProductPrice(),member.getCommission(),member.getCommissionProfit(),member.getAffliateCommission(),member.getUserCommission());
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setDatas(member.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("summaryCount Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
