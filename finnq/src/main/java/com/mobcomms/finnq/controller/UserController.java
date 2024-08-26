package com.mobcomms.finnq.controller;

import com.mobcomms.common.constant.Constant;
import com.mobcomms.finnq.dto.UserDto;
import com.mobcomms.finnq.dto.packet.UserPacket;
import com.mobcomms.finnq.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary  = "회원정보 동의여부 수정 요청", description  = "")
    @PostMapping(value = "/userAgreeTerms")
    public ResponseEntity<UserPacket.UpdateUserAgreeTerms.Response> updateUserAgreeTerms(@Valid @RequestBody UserPacket.UpdateUserAgreeTerms.UpdateUserAgreeTermsRequest request) throws Exception {
        var result = new UserPacket.UpdateUserAgreeTerms.Response();
        try {
            var userDto = new UserDto() {{
                setUserKey(request.getUserKey());
                setAgreeTerms(request.getUserKey());
            }};

            var postUserAgreeResult = userService.postUserAgree(userDto);
            if (postUserAgreeResult.getResultCode().equals(Constant.RESULT_CODE_SUCCESS)) {
                result.setSuccess();
                result.setData(postUserAgreeResult.getData());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                if (postUserAgreeResult.getResultMessage().equals(Constant.EMPTY_USER)) {
                    result.setError(Constant.EMPTY_USER);
                    return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
                } else {
                    result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, postUserAgreeResult.getResultMessage());
                    return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            log.error("Error updateUserAgreeTerms {}", e);
            result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary  = "회원정보 등록&수정 요청", description  = "")
    @PostMapping(value = "/userinfo")
    public ResponseEntity<UserPacket.PostUserinfo.Response> postUserinfo(@Valid @RequestBody UserPacket.PostUserinfo.PostUserinfoRequest request) throws Exception {
        var result = new UserPacket.PostUserinfo.Response();
        try {
            var userDto = new UserDto() {{
                setUserKey(request.getUserKey());
                setAgreeTerms(request.getUserKey());
                setAdId(request.getAdId());
                setOs(request.getOs());
            }};

            var PostUserInfo = userService.postUserInfo(userDto);

            if (PostUserInfo.getResultCode().equals(Constant.RESULT_CODE_SUCCESS)) {
                result.setSuccess();
                result.setData(PostUserInfo.getData());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, PostUserInfo.getResultMessage());
                return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Error postUserinfo {}", e);
            result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary  = "회원정보 조회", description  = "")
    @GetMapping(value = "/userinfo")
    public ResponseEntity<UserPacket.Response> getUserinfo(@Valid UserPacket.UserPacketBaseRequset request) throws Exception {
        var result = new UserPacket.Response();
        try {
            var userDto = new UserDto() {{
                setUserKey(request.getUserKey());
            }};
            var getUserInfo = userService.getUserInfo(userDto);

            if (getUserInfo.getResultCode().equals(Constant.RESULT_CODE_SUCCESS)) {
                result.setSuccess();
                result.setData(getUserInfo.getData());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                if (getUserInfo.getResultMessage().equals(Constant.EMPTY_USER)) {
                    result.setError(Constant.EMPTY_USER);
                    return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
                } else {
                    result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, getUserInfo.getResultMessage());
                    return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (Exception e) {
            log.error("Error getUserinfo {}", e);
            result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
