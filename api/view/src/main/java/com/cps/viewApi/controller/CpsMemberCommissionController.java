package com.cps.viewApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsMemberCommissionPacket;
import com.cps.cpsService.service.CpsMemberCommissionService;
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
    @PostMapping(value = "/memberCommission")
    public ResponseEntity<CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse> memberCommission(@Valid @RequestBody CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionRequest request) throws Exception {
        var result = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionResponse();

        try {
            var member = cpsMemberCommissionService.memberCommission(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setData(member.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("memberCommission Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 적립금 리스트 조회
     *
     * @date 2024-10-02
     */
    @Operation(summary = "회원 적립금 조회 리스트")
    @PostMapping(value = "/memberCommissionList")
    public ResponseEntity<CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListResponse> memberCommissionList(@Valid @RequestBody CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListRequest request) throws Exception {
        var result = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionListResponse();

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

    /**
     * 쿠팡 막대사탕 개수 조회
     *
     * @date 2024-10-08
     *
     */
    @Operation(summary = "쿠팡 막대사탕 개수 조회")
    @PostMapping(value = "/memberStick")
    public ResponseEntity<CpsMemberCommissionPacket.MemberCommissionInfo.MemberRewardUnitResponse> memberStick(@Valid @RequestBody CpsMemberCommissionPacket.MemberCommissionInfo.MemberCommissionRequest request) throws Exception {
        var result = new CpsMemberCommissionPacket.MemberCommissionInfo.MemberRewardUnitResponse();

        try {
            var member = cpsMemberCommissionService.memberStick(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setData(member.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("memberStick Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
