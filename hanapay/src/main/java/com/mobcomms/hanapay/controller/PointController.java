package com.mobcomms.hanapay.controller;

import com.mobcomms.common.constant.Constant;
import com.mobcomms.hanapay.dto.PointDto;
import com.mobcomms.hanapay.dto.UserDto;
import com.mobcomms.hanapay.dto.packet.PointPacket;
import com.mobcomms.hanapay.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PointController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PointService pointService;

    @Operation(summary  = "회원 머니박스 오늘 적립내역 조회", description  = "")
    @GetMapping(value = "/point")
    public ResponseEntity<PointPacket.Response> getPoint(PointPacket.PointPacketBaseRequset request) throws Exception {
        var result = new PointPacket.Response();
        try {
            var pointDto = new PointDto(){{
                setUserKey(request.getUserKey());
            }};
            var getPointInfo =  pointService.getPoint(pointDto);
            if (getPointInfo.getResultCode().equals(Constant.RESULT_CODE_SUCCESS)) {
                result.setSuccess();
                result.setData(getPointInfo.getData());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setError(getPointInfo.getResultMessage());
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Error getPoint {}", e);
            result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary  = "포인트 적립 요청", description  = "")
    @PostMapping(value = "/point")
    public ResponseEntity<PointPacket.PostPoint.Response> postPoint(PointPacket.PostPoint.PostPointRequest request) throws Exception {
        PointPacket.PostPoint.Response result = new PointPacket.PostPoint.Response();
        try {
            var pointDto = new PointDto(){{
                setUserKey(request.getUserKey());
                setAdId(request.getAdId());
                setAdName(request.getAdName());
            }};

            var postPoint =  pointService.postPoint(pointDto);

            if (postPoint.getResultCode().equals(Constant.RESULT_CODE_SUCCESS)) {
                result.setSuccess();
                result.setData(postPoint.getData());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setError(postPoint.getResultMessage());
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Error postPoint {}", e);
            result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary  = "광고 클릭에 대한 지급포인트 조회", description  = "")
    @GetMapping(value = "/point/info")
    public ResponseEntity<PointPacket.GetPointSetting.Response> getPointSetting() throws Exception {
        var result = new PointPacket.GetPointSetting.Response();
        try {
            var getPointSettingInfo =  pointService.getPointInfo();

            if (getPointSettingInfo.getResultCode().equals(Constant.RESULT_CODE_SUCCESS)) {
                result.setSuccess();
                result.setData(getPointSettingInfo.getData());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.setError(getPointSettingInfo.getResultMessage());
                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("Error getPointSetting {}", e);
            result.setApiMessage(Constant.RESULT_CODE_ERROR_SYSTEM, e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
