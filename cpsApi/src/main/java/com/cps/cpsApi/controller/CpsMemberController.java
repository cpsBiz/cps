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
public class CpsMemberController {

    private final CpsMemberService cpsMemberService;

    /**
     * 회원 등록, 수정, 삭제
     *
     * @date 2024-09-03
     */
    @Operation(summary = "회원 등록, 수정, 삭제")
    @PostMapping(value = "/userSignIn")
    public ResponseEntity<CpsMemberPacket.UserInfo.Response> userSignIn(@Valid @RequestBody CpsMemberPacket.UserInfo.UserRequest request) throws Exception {
        var result = new CpsMemberPacket.UserInfo.Response();

        try {
            var member = cpsMemberService.userSignIn(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setData(member.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("userSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 대행사 광고주 회원가입
     *
     * @date 2024-09-05
     */
    @Operation(summary = "대행사 광고주 회원 가입")
    @PostMapping(value = "/agencyUser")
    public ResponseEntity<CpsMemberPacket.UserInfo.Response> agencyUserSignIn(@Valid @RequestBody CpsMemberPacket.UserInfo.AgencyMemberRequest request) throws Exception {
        var result = new CpsMemberPacket.UserInfo.Response();

        try {
            if (request.getAgencyId().equals("linkprice")) {
                var member = cpsMemberService.linkPriceUserSignIn(request);
                if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                    result.setSuccess();
                } else {
                    result.setApiMessage(member.getResultCode(), member.getResultMessage());
                }
                result.setData(member.getData());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setApiMessage(Constants.AGENCY_BLANK.getCode(), Constants.AGENCY_BLANK.getValue());
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            result.setError("agencyUserSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
