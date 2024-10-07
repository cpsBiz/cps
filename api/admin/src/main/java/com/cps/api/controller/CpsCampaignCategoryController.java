package com.cps.api.controller;

import com.cps.common.constant.Constant;
import com.cps.common.utils.AES256Utils;
import com.cps.cpsService.packet.CpsCampaignCategoryPacket;
import com.cps.cpsService.service.CpsCampaignCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CpsCampaignCategoryController {

    private final CpsCampaignCategoryService cpsCampaignCategoryService;

    /**
     * 캠페인 카테고리 등록, 수정
     *
     * @date 2024-09-10
     */
    @Operation(summary = "캠페인 카테고리 등록, 수정")
    @PostMapping(value = "/campaignCategory1")
    public ResponseEntity<CpsCampaignCategoryPacket.CampaignCategoryInfo.Response> campaignCategory(@Valid @RequestBody List<CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategoryRequest> request) throws Exception {
        var result = new CpsCampaignCategoryPacket.CampaignCategoryInfo.Response();

        try {
            var campaignCategory = cpsCampaignCategoryService.campaignCategory(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaignCategory.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(campaignCategory.getResultCode(), campaignCategory.getResultMessage());
            }
            result.setDatas(campaignCategory.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("campaignCategory Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 캠페인 카테고리 조회
     *
     * @date 2024-09-10
     */
    @Operation(summary = "캠페인 카테고리 조회")
    @PostMapping(value = "/campaignCategoryList")
    public ResponseEntity<CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategorySearchResponse> campaignCategoryList(@Valid @RequestBody CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategorySearchRequest request) throws Exception {
        var result = new CpsCampaignCategoryPacket.CampaignCategoryInfo.CampaignCategorySearchResponse();

        try {
            var campaign = cpsCampaignCategoryService.campaignCategoryList(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaign.getResultCode())) {
                result.setSuccess();
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
}
