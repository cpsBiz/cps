package com.cps.clickDotPitch.controller;

import com.cps.common.packet.CpsClickPacket;
import com.cps.clickDotPitch.service.CpsClickDotPitchService;
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
public class CpsClickController {

    private final CpsClickDotPitchService cpsClickDotPitchService;

    /**
     * 캠페인 클릭
     *
     * @date 2024-09-04
     */
    @Operation(summary = "캠페인 클릭", description = "")
    @PostMapping(value = "/campaignClick")
    public ResponseEntity<CpsClickPacket.ClickInfo.Response> campaignClick(@Valid @RequestBody CpsClickPacket.ClickInfo.ClickRequest request) throws Exception {
        var result = new CpsClickPacket.ClickInfo.Response();

        try {
            var click = cpsClickDotPitchService.campaignClick(request);

            if (Constant.RESULT_CODE_SUCCESS.equals(click.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(click.getResultCode(), click.getResultMessage());
            }
            result.setData(click.getData());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
