package com.cps.cpsApi.controller;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.cpsApi.packet.CpsMemberPacket;
import com.cps.cpsApi.service.CpsMemberService;
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
public class CpsCampaignController {

    private final CpsMemberService cpsMemberService;

    /**
     * 캠페인 등록, 수정, 삭제
     *
     * @date 2024-09-03
     */
    @Operation(summary = "회원 등록, 수정, 삭제")
    @PostMapping(value = "/campaign")
    public ResponseEntity<CpsMemberPacket.MemberInfo.Response> campaign(@Valid @RequestBody CpsMemberPacket.MemberInfo.MemberRequest request) throws Exception {
        var result = new CpsMemberPacket.MemberInfo.Response();

        try {
            var member = cpsMemberService.memberSignIn(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setData(member.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("memberSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
