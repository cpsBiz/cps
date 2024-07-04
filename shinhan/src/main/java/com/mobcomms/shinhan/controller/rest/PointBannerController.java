package com.mobcomms.shinhan.controller.rest;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */

import com.mobcomms.common.utils.StringUtils;
import com.mobcomms.shinhan.dto.PointBannerInfoDto;
import com.mobcomms.shinhan.dto.packet.PointBannerPacket;
import com.mobcomms.shinhan.dto.PointDto;
import com.mobcomms.shinhan.dto.UserDto;
import com.mobcomms.shinhan.service.MemberService;
import com.mobcomms.shinhan.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banner")
public class PointBannerController {

    private final MemberService memberService;
    private final PointService pointService;

    //포인트 배너 정보 조회
    @GetMapping("/info")
    public ResponseEntity<PointBannerPacket.PostUserInfo.Response> getPointBannerInfo(PointBannerPacket.PostUserInfo.Request request) {
        //TODO : controller 오류 발생시 500 으로 처리 -> Service 에서 오류 발생시, 어떻게 반환
        PointBannerPacket.PostUserInfo.Response result = new PointBannerPacket.PostUserInfo.Response();
        //Reqest Data Check
        if(StringUtils.isNullOrEmpty(request.getUserKey())){
            result.setRequestError("userKey is null");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        try {
            var pointBannerInfo =  pointService.getPointBannerInfo(new PointBannerInfoDto(){{
                setUserKey(request.getUserKey());
                setOs(request.getOs());
                setAdid(request.getAdid());
            }});
            result.setSuccess();
            result.setData(pointBannerInfo.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {

            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gamezone/sadari/info")
    public ResponseEntity<PointBannerPacket.GetPointBannerInfo.Response> getSadariAdInfo(PointBannerPacket.GetPointBannerInfo.Request request) {

        var result = new PointBannerPacket.GetPointBannerInfo.Response();
        //Reqest Data Check
        if(StringUtils.isNullOrEmpty(request.getUserKey())){
            result.setRequestError("userKey is null");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        try {
            var pointBannerInfo =  pointService.getGameZoneAdInfo(new PointBannerInfoDto(){{
                setUserKey(request.getUserKey());
                setOs(request.getOs());
                setAdid(request.getAdid());
            }});
            result.setSuccess();
            result.setData(pointBannerInfo.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //회원 insert or update
    @PostMapping("userinfo")
    public ResponseEntity<PointBannerPacket.GetSadariAdInfo.Response> postUserInfo(@RequestBody PointBannerPacket.GetSadariAdInfo.Request request) {
        var result = new PointBannerPacket.GetSadariAdInfo.Response();
        //Reqest Data Check
        if(StringUtils.isNullOrEmpty(request.getUserKey())){
            result.setRequestError("userKey is null");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        memberService.joinOrEdit(new UserDto(){{
            setUserKey(request.getUserKey());
            setUserAppOs(request.getOs());
            setAdid(request.getAdid());
        }});

        result.setSuccess();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //포인트 적립 요청
    @PostMapping("/save")
    public ResponseEntity<PointBannerPacket.PostPoint.Response> postPoint(@RequestBody PointBannerPacket.PostPoint.Request request) {
        var result = new PointBannerPacket.PostPoint.Response();
        //Reqest Data Check
        if(StringUtils.isNullOrEmpty(request.getUserKey()) ||
                StringUtils.isNullOrEmpty(request.getZoneId())){
            result.setRequestError("requset data is wrong");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        var saveResult = pointService.callAPIPoint(new PointDto(){{
            setUserKey(request.getUserKey());
            setZoneId(request.getZoneId());
            setOs(request.getOs());
            setAdUrl(request.getAdUrl());
        }});

        result.setResultCode(saveResult.getResultCode());
        result.setResultMessage(saveResult.getResultMessage());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
