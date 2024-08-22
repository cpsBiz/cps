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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banner")
@Slf4j
public class PointBannerController {
    private final MemberService memberService;
    private final PointService pointService;

    //포인트 배너 정보 조회
    @GetMapping("/info")
    public ResponseEntity<PointBannerPacket.GetPointBannerInfo.Response> getPointBannerInfo(@Valid PointBannerPacket.GetPointBannerInfo.Request request) {
        PointBannerPacket.GetPointBannerInfo.Response result = new PointBannerPacket.GetPointBannerInfo.Response();
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
            log.error("ERROR", e);
            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //사다리 타기 배너 정보 조회
    @GetMapping("/gamezone/sadari/info")
    public ResponseEntity<PointBannerPacket.GetSadariAdInfo.Response> getSadariAdInfo(@Valid PointBannerPacket.GetSadariAdInfo.Request request) {
        var result = new PointBannerPacket.GetSadariAdInfo.Response();
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
            log.error("ERROR", e);
            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //회원 insert or update
    @PostMapping("userinfo")
    public ResponseEntity<PointBannerPacket.PostUserInfo.Response> postUserInfo(@Valid @RequestBody PointBannerPacket.PostUserInfo.Request request) {
        var result = new PointBannerPacket.PostUserInfo.Response();
        try {
            memberService.joinOrEdit(new UserDto(){{
                setUserKey(request.getUserKey());
                setUserAppOs(request.getOs());
                setAdid(request.getAdid());
            }});

            result.setSuccess();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR", e);
            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //포인트 적립 요청
    @PostMapping("/save")
    public ResponseEntity<PointBannerPacket.PostPoint.Response> postPoint(@Valid @RequestBody PointBannerPacket.PostPoint.Request request) {
        var result = new PointBannerPacket.PostPoint.Response();
        try {
            var saveResult = pointService.callAPIPoint(new PointDto() {{
                setUserKey(request.getUserKey());
                setZoneId(request.getZoneId());
                setOs(request.getOs());
                setAdUrl(request.getAdUrl());
            }});

            result.setResultCode(saveResult.getResultCode());
            result.setResultMessage(saveResult.getResultMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR", e);
            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
