package com.mobcomms.sample.controller.rest;

import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.model.PayboocPacket;
import com.mobcomms.sample.service.PayboocHttpService;
import com.mobcomms.sample.service.WebClientSampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor

@RequestMapping("/api/webclient")
public class WebclientSampleController {

    private final WebClientSampleService webClientSampleService;
    private final PayboocHttpService payboocHttpService;

    @GetMapping("")
    @Operation(summary = "통신테스트!", description = "weblcient!")
    public Mono<ResponseEntity<String>> callWebclient() {
        return webClientSampleService.sampleData().map(data -> ResponseEntity.ok(data));
    }

    @GetMapping("/get-test")
    @Operation(summary = "Test get 통신모듈테스트!", description = "test get weblcient ")
    public ResponseEntity<PayboocPacket.GetUserInfo.Response> testGetCallWebclient(PayboocPacket.GetUserInfo.Requset requset) {
        var rt = payboocHttpService.GetUserInfo(requset);
        return ResponseEntity.ok(rt);
    }

    @GetMapping("/get-test-async")
    @Operation(summary = "Test get 통신모듈테스트!", description = "test get weblcient ")
    public Mono<ResponseEntity<PayboocPacket.GetUserInfo.Response>> testGetAsyncCallWebclient(PayboocPacket.GetUserInfo.Requset requset) {
        var rt = payboocHttpService.GetUserInfoAsync(requset);
        return rt.map(data -> ResponseEntity.ok(data));
    }


    @PostMapping("/post-test")
    @Operation(summary = "Test post 통신모듈테스트!", description = "test post weblcient ")
    public ResponseEntity<PayboocPacket.PostUserInfo.Response> testpostCallWebclient(@RequestBody PayboocPacket.PostUserInfo.Requset requset) {
        var rt = payboocHttpService.PostUserInfo(requset);
        return ResponseEntity.ok(rt);
    }

    //form 전송 테스트
    @PostMapping("/post-test-form")
    @Operation(summary = "Test post 통신모듈테스트!", description = "test post form data weblcient ")
    public ResponseEntity<PayboocPacket.PostUserInfo.Response> testpostWithFormCallWebclient(@RequestBody PayboocPacket.PostUserInfo.Requset requset) {

        var rt = payboocHttpService.PostFormUserInfo(requset);
        return ResponseEntity.ok(rt);
    }

    @GetMapping("/dynamic-property")
    public String getDynamicProperty() {
        return "dynamicProperty";
    }

}
