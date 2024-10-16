package com.cps.api.controller;

import com.cps.api.service.GiftiShowService;
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

    private final CpsGiftService cpsGiftService;

    private final GiftiShowService giftiShowService;

    /**
     * 브랜드 등록
     *
     * @date 2024-10-02
     */
    @Operation(summary = "브랜드 등록, 수정, 삭제")
    @PostMapping(value = "/giftBrand")
    public ResponseEntity<CpsGiftPacket.GiftInfo.GiftBrandResponse> giftBrand(@Valid @RequestBody CpsGiftPacket.GiftInfo.GiftBrandRequest request) throws Exception {
        var result = new CpsGiftPacket.GiftInfo.GiftBrandResponse();

        try {
            var campaignCategory = cpsGiftService.giftBrand(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaignCategory.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(campaignCategory.getResultCode(), campaignCategory.getResultMessage());
            }
            result.setData(campaignCategory.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("giftBrand Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 브랜드 별 상품 상품 등록
     *
     * @date 2024-10-02
     */
    @Operation(summary = "브랜드 별 상품 등록, 수정, 삭제")
    @PostMapping(value = "/giftProduct")
    public ResponseEntity<CpsGiftPacket.GiftInfo.GiftProductResponse> giftProduct(@Valid @RequestBody CpsGiftPacket.GiftInfo.GiftProductRequest request) throws Exception {
        var result = new CpsGiftPacket.GiftInfo.GiftProductResponse();

        try {
            var campaignCategory = cpsGiftService.giftProduct(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaignCategory.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(campaignCategory.getResultCode(), campaignCategory.getResultMessage());
            }
            result.setData(campaignCategory.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("giftProduct Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 기프트 상품 등록
     *
     * @date 2024-10-16
     */
    @Operation(summary = "기프트 상품 등록, 수정, 삭제")
    @PostMapping(value = "/product")
    public ResponseEntity<CpsGiftPacket.GiftInfo.GiftProductResponse> product(@Valid @RequestBody CpsGiftPacket.GiftInfo.CpsGiftRequest request) throws Exception {
        var result = new CpsGiftPacket.GiftInfo.GiftProductResponse();

        try {
            var campaignCategory = cpsGiftService.product(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(campaignCategory.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(campaignCategory.getResultCode(), campaignCategory.getResultMessage());
            }
            result.setData(campaignCategory.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("product Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "기프티쇼 상품 가져오기")
    @PostMapping(value = "/giftiShowBizProduct")
    public void giftiShowBizProduct(){
        giftiShowService.giftiShowBizProduct();
    }


}
