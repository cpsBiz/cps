package com.cps.cpsViewApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsViewApi.packet.CpsMemberCommissionPacket;
import com.cps.cpsViewApi.packet.CpsViewPacket;
import com.cps.cpsViewApi.service.CpsMemberCommissionService;
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
public class CpsMemberCommissionController {

    private final CpsMemberCommissionService cpsMemberCommissionService;

    /**
     * 회원 적립금 조회
     *
     * @date 2024-10-02
     */
    @Operation(summary = "회원 적립금 조회")
    @PostMapping(value = "/memberCommissionList")
    public ResponseEntity<CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse> memberCommissionList(@Valid @RequestBody CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionRequest request) throws Exception {
        var result = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse();

        try {
            var member = cpsMemberCommissionService.memberCommissionList(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setDatas(member.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("memberCommissionList Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
