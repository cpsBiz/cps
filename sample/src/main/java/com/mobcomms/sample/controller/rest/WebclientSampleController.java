package com.mobcomms.sample.controller.rest;

import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.service.WebClientSampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webclient")
public class WebclientSampleController {

    private final WebClientSampleService webClientSampleService;

    @GetMapping("")
    @Operation(summary = "통신테스트!", description = "weblcient!")
    public Mono<ResponseEntity<List<SampleDto>>> callWebclient() {
        return webClientSampleService.sampleData().map(data -> ResponseEntity.ok(data));
    }
}
