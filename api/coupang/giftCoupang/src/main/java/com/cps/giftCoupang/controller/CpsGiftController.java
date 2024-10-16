package com.cps.giftCoupang.controller;

import com.cps.cpsService.packet.CpsClickPacket;
import com.cps.giftCoupang.packet.CpsCoupangStickPacket;
import com.cps.giftCoupang.service.CpsGiftCoupangService;
import com.cps.common.constant.Constant;
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

    private final CpsGiftCoupangService cpsGiftCoupangService;

    /**
     * 쿠팡 막대사탕 총 합 조회
     *
     * @date 2024-10-15
     */
    @Operation(summary = "쿠팡 막대사탕 개수 조회", description = "")
    @PostMapping(value = "/coupangStick")
    public ResponseEntity<CpsCoupangStickPacket.CoupangStickInfo.CoupangStickResponse> coupangStick(@Valid @RequestBody CpsCoupangStickPacket.CoupangStickInfo.CoupangStickRequest request) throws Exception {
        var result = new CpsCoupangStickPacket.CoupangStickInfo.CoupangStickResponse();

        try {
            var stick = cpsGiftCoupangService.coupangStick(request);

            if (Constant.RESULT_CODE_SUCCESS.equals(stick.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(stick.getResultCode(), stick.getResultMessage());
            }
            result.setData(stick.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 쿠팡 막대사탕 리스트 조회
     *
     * @date 2024-10-15
     */
    @Operation(summary = "쿠팡 막대사탕 리스트 조회", description = "")
    @PostMapping(value = "/coupangStickList")
    public ResponseEntity<CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListResponse> coupangStickList(@Valid @RequestBody CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListRequest request) throws Exception {
        var result = new CpsCoupangStickPacket.CoupangStickInfo.CoupangStickListResponse();

        try {
            var stick = cpsGiftCoupangService.coupangStickList(request);

            if (Constant.RESULT_CODE_SUCCESS.equals(stick.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(stick.getResultCode(), stick.getResultMessage());
            }
            result.setDatas(stick.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 쿠팡 막대사탕 구매
     *
     * @date 2024-10-16
     */
    @Operation(summary = "쿠팡 막대사탕 구매", description = "")
    @PostMapping(value = "/coupangGift")
    public ResponseEntity<CpsCoupangStickPacket.CoupangStickInfo.CoupangGiftResponse> coupangGift(@Valid @RequestBody CpsCoupangStickPacket.CoupangStickInfo.CoupangStickGiftRequest request) throws Exception {
        var result = new CpsCoupangStickPacket.CoupangStickInfo.CoupangGiftResponse();

        try {
            var stick = cpsGiftCoupangService.coupangGift(request);

            if (Constant.RESULT_CODE_SUCCESS.equals(stick.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(stick.getResultCode(), stick.getResultMessage());
            }
            result.setData(stick.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}