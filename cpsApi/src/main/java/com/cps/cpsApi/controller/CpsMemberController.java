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
    @PostMapping(value = "/memberSignIn")
    public ResponseEntity<CpsMemberPacket.MemberInfo.Response> memberSignIn(@Valid @RequestBody CpsMemberPacket.MemberInfo.MemberRequest request) throws Exception {
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

    /**
     * 대행사 광고주 회원가입
     *
     * @date 2024-09-05
     */
    @Operation(summary = "대행사 광고주 회원 가입")
    @PostMapping(value = "/agencyMember")
    public ResponseEntity<CpsMemberPacket.MemberInfo.Response> agencyMemberSignIn(@Valid @RequestBody CpsMemberPacket.MemberInfo.AgencyMemberRequest request) throws Exception {
        var result = new CpsMemberPacket.MemberInfo.Response();

        try {
            if (request.getAgencyId().equals("linkprice")) {
                var member = cpsMemberService.linkPriceMemberSignIn(request);
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
            result.setError("memberSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 조회
     *
     * @date 2024-09-09
     */
    @Operation(summary = "회원 조회")
    @PostMapping(value = "/memberSearch")
    public ResponseEntity<CpsMemberPacket.MemberInfo.MemberSearchResponse> memberSearch(@Valid @RequestBody CpsMemberPacket.MemberInfo.MemberSearcgRequest request) throws Exception {
        var result = new CpsMemberPacket.MemberInfo.MemberSearchResponse();

        try {
            var member = cpsMemberService.memberSearch(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setDatas(member.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("memberSearch Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
