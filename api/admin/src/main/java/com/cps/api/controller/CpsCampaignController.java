package com.cps.api.controller;

import com.cps.common.constant.Constant;
import com.cps.common.packet.CpsCampaignPacket;
import com.cps.common.service.CpsCampaignService;
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
public class CpsCampaignController {

    private final CpsCampaignService cpsCampaignService;

    /**
     * 캠페인 등록, 수정, 삭제
     *
     * @date 2024-09-10
     */
    @Operation(summary = "캠페인 등록, 수정, 삭제")
    @PostMapping(value = "/campaign")
    public ResponseEntity<CpsCampaignPacket.CampaignInfo.Response> campaign(@Valid @RequestBody CpsCampaignPacket.CampaignInfo.CampaignRequest request) throws Exception {
        var result = new CpsCampaignPacket.CampaignInfo.Response();

        try {
            var campaign = cpsCampaignService.campaign(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaign.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(campaign.getResultCode(), campaign.getResultMessage());
            }
            result.setData(campaign.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("campaign Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 캠페인 조회
     *
     * @date 2024-09-10
     */
    @Operation(summary = "캠페인 조회")
    @PostMapping(value = "/campaignList")
    public ResponseEntity<CpsCampaignPacket.CampaignInfo.CampaignSearchResponse> campaignList(@Valid @RequestBody CpsCampaignPacket.CampaignInfo.CampaignSearchRequest request) throws Exception {
        var result = new CpsCampaignPacket.CampaignInfo.CampaignSearchResponse();

        try {
            var campaign = cpsCampaignService.campaignList(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaign.getResultCode())) {
                result.setSuccess(campaign.getTotalCount());
            } else {
                result.setApiMessage(campaign.getResultCode(), campaign.getResultMessage());
            }
            result.setDatas(campaign.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("campaignSearch Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 캠페인 카테고리 일괄 수정
     *
     * @date 2024-10-01
     */
    @Operation(summary = "캠페인 카테고리 일괄 수정")
    @PostMapping(value = "/campaignCategory")
    public ResponseEntity<CpsCampaignPacket.CampaignInfo.CampaignCategoryResponse> campaignCategory(@Valid @RequestBody CpsCampaignPacket.CampaignInfo.CampaignCategoryListRequest request) throws Exception {
        var result = new CpsCampaignPacket.CampaignInfo.CampaignCategoryResponse();

        try {
            var campaign = cpsCampaignService.campaignCategory(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaign.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(campaign.getResultCode(), campaign.getResultMessage());
            }
            result.setDatas(campaign.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("campaignCategory Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
