package com.cps.cpsApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsApi.packet.CpsAffiliateCampaignPacket;
import com.cps.cpsApi.service.CpsAffiliateCampaignService;
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
public class CpsAffiliateCampaignController {

    private final CpsAffiliateCampaignService cpsAffiliateCampaignService;

    /**
     * 매체 캠페인 승인
     *
     * @date 2024-09-12
     */
    @Operation(summary = "매체 캠페인 승인")
    @PostMapping(value = "/affiliateCampaign")
    public ResponseEntity<CpsAffiliateCampaignPacket.CpsAffiliateCampaignInfo.Response> affiliateCampaign(@Valid @RequestBody CpsAffiliateCampaignPacket.CpsAffiliateCampaignInfo.CpsAffiliateCampaignRequest request) throws Exception {
        var result = new CpsAffiliateCampaignPacket.CpsAffiliateCampaignInfo.Response();

        try {
            var member = cpsAffiliateCampaignService.affiliateCampaign(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setDatas(member.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("affiliateCampaign Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
