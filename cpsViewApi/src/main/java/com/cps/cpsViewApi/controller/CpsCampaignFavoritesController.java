package com.cps.cpsViewApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsViewApi.packet.CpsCampaignFavoritesPacket;
import com.cps.cpsViewApi.packet.CpsViewPacket;
import com.cps.cpsViewApi.service.CpsCampaignFavoritesService;
import com.cps.cpsViewApi.service.CpsViewService;
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
public class CpsCampaignFavoritesController {

    private final CpsCampaignFavoritesService cpsCampaignFavoritesService;

    /**
     * 캠페인 즐겨찾기 등록, 삭제
     *
     * @date 2024-10-01
     */
    @Operation(summary = "캠페인 즐겨찾기 등록, 삭제")
    @PostMapping(value = "/favorites")
    public ResponseEntity<CpsCampaignFavoritesPacket.CampaignFavoritesInfo.CampaignFavoritesResponse> favorites(@Valid @RequestBody CpsCampaignFavoritesPacket.CampaignFavoritesInfo.CampaignFavoritesRequest request) throws Exception {
        var result = new CpsCampaignFavoritesPacket.CampaignFavoritesInfo.CampaignFavoritesResponse();

        try {
            var member = cpsCampaignFavoritesService.favorites(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setDatas(member.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("view Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
