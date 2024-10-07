package com.cps.viewApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsViewPacket;
import com.cps.cpsService.service.CpsViewService;
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
public class CpsViewController {

    private final CpsViewService cpsViewService;

    /**
     * 캠페인 노출 조회 등록
     *
     * @date 2024-09-11
     */
    @Operation(summary = "캠페인 노출 조회")
    @PostMapping(value = "/campaignView")
    public ResponseEntity<CpsViewPacket.ViewInfo.ViewResponse> campaignView(@Valid @RequestBody CpsViewPacket.ViewInfo.ViewRequest request) throws Exception {
        var result = new CpsViewPacket.ViewInfo.ViewResponse();

        try {
            var member = cpsViewService.campaignView(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setDatas(member.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("campaignView Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
