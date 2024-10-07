package com.cps.api.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsCampaignCommissionPacket;
import com.cps.cpsService.service.CpsCampaignCommissionService;
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
public class CpsCampaignCommissionController {

    private final CpsCampaignCommissionService cpsCampaignCommissionService;

    /**
     * 매체 캠페인 승인
     *
     * @date 2024-09-12
     */
    @Operation(summary = "매체 캠페인 승인")
    @PostMapping(value = "/campaignCommission")
    public ResponseEntity<CpsCampaignCommissionPacket.AffiliateCampaignInfo.Response> campaignCommission(@Valid @RequestBody CpsCampaignCommissionPacket.AffiliateCampaignInfo.AffiliateCampaignRequest request) throws Exception {
        var result = new CpsCampaignCommissionPacket.AffiliateCampaignInfo.Response();

        try {
            var commission = cpsCampaignCommissionService.campaignCommission(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(commission.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(commission.getResultCode(), commission.getResultMessage());
            }
            result.setData(commission.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("affiliateCampaign Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
