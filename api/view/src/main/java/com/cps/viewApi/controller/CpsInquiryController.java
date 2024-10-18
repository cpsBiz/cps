package com.cps.viewApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsOnetoOnePacket;
import com.cps.cpsService.service.CpsOneToOneService;
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
public class CpsInquiryController {

    private final CpsOneToOneService cpsOneToOneService;

    /**
     * 문의 등록
     *
     * @date 2024-10-01
     */
    @Operation(summary = "문의 등록")
    @PostMapping(value = "/inquiry")
    public ResponseEntity<CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryResponse> inquiry(@Valid @RequestBody CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryListRequest request) throws Exception {
        var result = new CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryResponse();

        try {
            var inquiry = cpsOneToOneService.inquiry(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(inquiry.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(inquiry.getResultCode(), inquiry.getResultMessage());
            }
            result.setData(inquiry.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("inquiry Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
