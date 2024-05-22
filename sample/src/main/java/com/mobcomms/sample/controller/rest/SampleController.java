package com.mobcomms.sample.controller.rest;

import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1")
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("")
    @Operation(summary = "읽기!", description = "select!")
    public ResponseEntity<List<SampleDto>> readSample() {
        return ResponseEntity.ok(sampleService.readSample());}

    @PostMapping("")
    @Operation(summary = "쓰기!", description = "insert!")
    public ResponseEntity<SampleDto> createSample(@RequestBody SampleDto dto) {
        return ResponseEntity.ok(sampleService.createSample(dto));
    }

    @PatchMapping("")
    @Operation(summary = "수정하기", description = "update!")
    public ResponseEntity<SampleDto> updateSample(@RequestBody SampleDto dto) {
        return ResponseEntity.ok(sampleService.updateSample(dto));
    }

    @PutMapping("")
    @Operation(summary = "대체하기(전체내용수정)!", description = "delete & insert!")
    public ResponseEntity<SampleDto> deleteInsertSample(@RequestBody SampleDto dto) throws Exception {
        return ResponseEntity.ok(sampleService.deleteInsertSample(dto));
    }

    @DeleteMapping("")
    @Operation(summary = "삭제하기!", description = "delete!")
    public ResponseEntity<SampleDto> deleteSample(@RequestBody SampleDto dto) {
        sampleService.deleteSample(dto);
        return ResponseEntity.ok(dto);
    }
}
