package com.cps.api.controller;

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
public class CpsOneToOneController {

    private final CpsOneToOneService cpsOneToOneService;

    /**
     * 문의 답변 등록
     *
     * @date 2024-10-01
     */
    @Operation(summary = "문의 답변 등록")
    @PostMapping(value = "/answer")
    public ResponseEntity<CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse> answer(@Valid @RequestBody CpsOnetoOnePacket.CpsOnetoOneInfo.OnetoOneRequest request) throws Exception {
        var result = new CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse();

        try {
            var answer = cpsOneToOneService.answer(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(answer.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(answer.getResultCode(), answer.getResultMessage());
            }
            result.setData(answer.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("inquiry Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 문의 리스트
     *
     * @date 2024-10-01
     */
    @Operation(summary = "문의 리스트")
    @PostMapping(value = "/inquiryList")
    public ResponseEntity<CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryPageResponse> inquiryList(@Valid @RequestBody CpsOnetoOnePacket.CpsOnetoOneInfo.InquirySearchRequest request) throws Exception {
        var result = new CpsOnetoOnePacket.CpsOnetoOneInfo.InquiryPageResponse();

        try {
            var inquiry = cpsOneToOneService.inquiryList(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(inquiry.getResultCode())) {
                result.setSuccess(inquiry.getTotalCount());
            } else {
                result.setApiMessage(inquiry.getResultCode(), inquiry.getResultMessage());
            }
            result.setDatas(inquiry.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("inquirySearch Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 문의 상세 조회
     *
     * @date 2024-10-01
     */
    @Operation(summary = "문의 상세 조회")
    @PostMapping(value = "/inquiryDetail")
    public ResponseEntity<CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse> inquiryDetail(@Valid @RequestBody CpsOnetoOnePacket.CpsOnetoOneInfo.OnetoOneRequest request) throws Exception {
        var result = new CpsOnetoOnePacket.CpsOnetoOneInfo.OneToOneResponse();

        try {
            var inquiry = cpsOneToOneService.inquiryDetail(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(inquiry.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(inquiry.getResultCode(), inquiry.getResultMessage());
            }
            result.setData(inquiry.getData ());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("inquiryDetail Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
