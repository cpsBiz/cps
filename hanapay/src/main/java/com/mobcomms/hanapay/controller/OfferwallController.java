package com.mobcomms.hanapay.controller;

import com.mobcomms.hanapay.dto.OfferwallDto;
import com.mobcomms.hanapay.dto.PointDto;
import com.mobcomms.hanapay.dto.packet.OfferwallPacket;
import com.mobcomms.hanapay.service.OfferwallService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OfferwallController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OfferwallService offerwallService;

    @Operation(summary  = "오퍼월 포미션 url 조회", description  = "")
    @GetMapping(value = "/offerwall/pomission")
    public ResponseEntity<OfferwallPacket.GetOfferwall.Response> getOfferwall() throws Exception {
        var result = new OfferwallPacket.GetOfferwall.Response();
        try {
            var offerwall =  offerwallService.getPomission();
            result.setResultCode(offerwall.getResultCode());
            result.setResultMessage(offerwall.getResultMessage());
            result.setData(offerwall.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getUserinfo ERROR", e);
            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary  = "오퍼월 > 포미션 적립 요청 (허용된 IP만 접근 가능)", description  = "")
    @PostMapping(value = "/offerwall/pomission")
    public ResponseEntity<OfferwallPacket.PostOfferwall.Response> postOfferwall(OfferwallPacket.PostOfferwall.Request request) throws Exception {
        var result = new OfferwallPacket.PostOfferwall.Response();
        try {
            var offerwallDto = new OfferwallDto(){{
                setUserKey(request.getUserKey());
                setUserPoint(request.getUserPoint());
                setAdverName(request.getAdverName());
                setMissionType(request.getMissionType());
            }};

            var offerwall =  offerwallService.postPomission(offerwallDto);
            result.setResultCode(offerwall.getResultCode());
            result.setResultMessage(offerwall.getResultMessage());
            result.setData(offerwall.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ERROR", e);
            result.setError(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}