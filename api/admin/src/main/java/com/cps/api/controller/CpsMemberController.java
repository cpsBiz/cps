package com.cps.api.controller;

import com.cps.common.constant.Constant;
import com.cps.common.constant.Constants;
import com.cps.common.packet.CpsMemberPacket;
import com.cps.common.service.CpsMemberService;
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
    public ResponseEntity<CpsMemberPacket.MemberInfo.Response> memberSignIn(@Valid @RequestBody CpsMemberPacket.MemberInfo.MemberSiteListRequest request) throws Exception {
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
    @PostMapping(value = "/agencyUser")
    public ResponseEntity<CpsMemberPacket.MemberInfo.Response> agencyMemberSignIn(@Valid @RequestBody CpsMemberPacket.MemberInfo.AgencyMemberRequest request) throws Exception {
        var result = new CpsMemberPacket.MemberInfo.Response();

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
            result.setError("agencyMemberSignIn Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 조회
     *
     * @date 2024-09-30
     */
    @Operation(summary = "회원 조회")
    @PostMapping(value = "/memberList")
    public ResponseEntity<CpsMemberPacket.MemberInfo.MemberListResponse> memberList(@Valid @RequestBody CpsMemberPacket.MemberInfo.MemberListRequest request) throws Exception {
        var result = new CpsMemberPacket.MemberInfo.MemberListResponse();

        try {
            var member = cpsMemberService.memberList(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess(member.getTotalCount());
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

    /**
     * 회원 상세 조회
     *
     * @date 2024-09-30
     */
    @Operation(summary = "회원 상세 조회")
    @PostMapping(value = "/memberDetail")
    public ResponseEntity<CpsMemberPacket.MemberInfo.MemberDetailResponse> memberDetail(@Valid @RequestBody CpsMemberPacket.MemberInfo.MemberDetail request) throws Exception {
        var result = new CpsMemberPacket.MemberInfo.MemberDetailResponse();

        try {
            var member = cpsMemberService.memberDetail(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setData(member.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("memberDetail Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
