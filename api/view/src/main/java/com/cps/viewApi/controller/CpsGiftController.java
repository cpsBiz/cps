package com.cps.viewApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsGiftBrandPacket;
import com.cps.cpsService.packet.CpsGiftProductPacket;
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
     * @date 2024-10-17
     */
    @Operation(summary = "기프트 브랜드 리스트")
    @PostMapping(value = "/giftBrandList")
    public ResponseEntity<CpsGiftBrandPacket.BrandInfo.GiftBrandResponse> giftBrandList(@Valid @RequestBody CpsGiftBrandPacket.BrandInfo.BradnRequest request) throws Exception {
        var result = new CpsGiftBrandPacket.BrandInfo.GiftBrandResponse();

        try {
            var brand = gpsGiftService.giftBrandList(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(brand.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(brand.getResultCode(), brand.getResultMessage());
            }
            result.setDatas(brand.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("giftBrandList Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 기프트 상품 리스트
     *
     * @date 2024-10-17
     */
    @Operation(summary = "기프트 상품 리스트")
    @PostMapping(value = "/giftProductList")
    public ResponseEntity<CpsGiftProductPacket.GiftProductInfo.GiftProductResponse> giftProductList(@Valid @RequestBody CpsGiftProductPacket.GiftProductInfo.GiftProductRequest request) throws Exception {
        var result = new CpsGiftProductPacket.GiftProductInfo.GiftProductResponse();

        try {
            var brand = gpsGiftService.giftProductList(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(brand.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(brand.getResultCode(), brand.getResultMessage());
            }
            result.setDatas(brand.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("giftProductList Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
