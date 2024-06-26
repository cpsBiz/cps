package com.mobcomms.shinhan.controller.rest;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */

import com.mobcomms.shinhan.dto.PointBannerInfoDto;
import com.mobcomms.shinhan.dto.PointBannerPacket;
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
@RequestMapping("/api/v1/point-banner")
public class PointBannerController {

    private final MemberService memberService;
    private final PointService pointService;

    //포인트 배너 정보 조회
    @GetMapping("/info")
    public ResponseEntity<PointBannerPacket.PointBannerInfo.Response> pointBannerInfo(PointBannerPacket.PointBannerInfo.Request request) {
        var pointBannerInfo =  pointService.getPointBannerInfo(new PointBannerInfoDto(){{
            setUserKey(request.getUserKey());
        }});
        var result =  new PointBannerPacket.PointBannerInfo.Response();
        result.setSuccess();
        result.setData(pointBannerInfo.getData());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //회원 insert or update
    @PostMapping("userinfo")
    public PointBannerPacket.UserInfo.Response userInfo(PointBannerPacket.UserInfo.Request request) {
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
