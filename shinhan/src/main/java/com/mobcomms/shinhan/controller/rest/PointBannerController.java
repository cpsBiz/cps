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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banner")
public class PointBannerController {

    private final MemberService memberService;
    private final PointService pointService;

    //포인트 배너 정보 조회
    @GetMapping("/info")
    public ResponseEntity<PointBannerPacket.PointBannerInfo.Response> pointBannerInfo(PointBannerPacket.PointBannerInfo.Request request) {
        //TODO : controller 오류 발생시 500 으로 처리 -> Service 에서 오류 발생시, 어떻게 반환
        PointBannerPacket.PointBannerInfo.Response result = new PointBannerPacket.PointBannerInfo.Response();
        //Reqest Data Check
        if(StringUtils.isNullOrEmpty(request.getUserKey())){
            result.setRequestError("userKey is null");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        try {
            var pointBannerInfo =  pointService.getPointBannerInfo(new PointBannerInfoDto(){{
                setUserKey(request.getUserKey());
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
    public PointBannerPacket.UserInfo.Response userInfo(PointBannerPacket.UserInfo.Request request) {
        if(StringUtils.isNullOrEmpty(request.getUserKey())){
            return new PointBannerPacket.UserInfo.Response(){{
                setError("userKey is null");
            }};
        }



        memberService.joinOrEdit(new UserDto(){{
            setUserKey(request.getUserKey());
            setUserAppOs(request.getOs());
            setAdid(request.getAdid());
        }});

        var result = new PointBannerPacket.UserInfo.Response();
        result.setSuccess();

        return result;
    }

    //포인트 적립 요청
    @PostMapping("/save")
    public PointBannerPacket.Point.Response point(PointBannerPacket.Point.Request request) {
        pointService.callAPIPoint(new PointDto(){{
            setUserKey(request.getUserKey());
            setZoneId(request.getZoneId());
            setOs(request.getOs());
        }});

        return new PointBannerPacket.Point.Response();
    }
}
