package com.cps.viewApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsGiftPacket;
import com.cps.cpsService.service.CpsGiftService;
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
public class CpsGiftController {

    private final CpsGiftService gpsGiftService;

    /**
     * 기프트 브랜드 리스트
     *
     * @date 2024-10-02
     */
    @Operation(summary = "기프트 브랜드 리스트")
    @PostMapping(value = "/giftBrandList")
    public ResponseEntity<CpsGiftPacket.GiftInfo.GiftBrandResponse> giftBrandList(@Valid @RequestBody CpsGiftPacket.GiftInfo.GiftBrandRequest request) throws Exception {
        var result = new CpsGiftPacket.GiftInfo.GiftBrandResponse();

        try {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("giftBrandList Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *  선물 당첨
     *
     * @date 2024-10-16
     */
    @Operation(summary = "선물 당첨")
    @PostMapping(value = "/giftProbability")
    public CpsGiftPacket.GiftInfo.GiftProductResponse giftProbability(@Valid @RequestBody CpsGiftPacket.GiftInfo.GiftRequest request) throws Exception {
        var result = new CpsGiftPacket.GiftInfo.GiftProductResponse();

        try {
            var gift = gpsGiftService.giftProbability(request);
        } catch (Exception e) {
            result.setError("giftProbability Controller Error");
        }
        return result;
    }
}
