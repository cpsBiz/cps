package com.mobcomms.shinhan.controller.rest;

import com.mobcomms.shinhan.dto.ShinhanSampleDto;
import com.mobcomms.shinhan.service.ShinhanSampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1")
public class ShinhanSampleController {

    private final ShinhanSampleService sampleService;

    @GetMapping("")
    @Operation(summary = "읽기!", description = "select!")
    public ResponseEntity<List<ShinhanSampleDto>> readSample() {
        return ResponseEntity.ok(sampleService.readSample());}

    @PostMapping("")
    @Operation(summary = "쓰기!", description = "insert!")
    public ResponseEntity<ShinhanSampleDto> createSample(@RequestBody ShinhanSampleDto dto) {
        return ResponseEntity.ok(sampleService.createSample(dto));
    }

    @PatchMapping("")
    @Operation(summary = "수정하기", description = "update!")
    public ResponseEntity<ShinhanSampleDto> updateSample(@RequestBody ShinhanSampleDto dto) {
        return ResponseEntity.ok(sampleService.updateSample(dto));
    }

    @PutMapping("")
    @Operation(summary = "대체하기(전체내용수정)!", description = "delete & insert!")
    public ResponseEntity<ShinhanSampleDto> deleteInsertSample(@RequestBody ShinhanSampleDto dto) throws Exception {
        return ResponseEntity.ok(sampleService.deleteInsertSample(dto));
    }

    @DeleteMapping("")
    @Operation(summary = "삭제하기!", description = "delete!")
    public ResponseEntity<ShinhanSampleDto> deleteSample(@RequestBody ShinhanSampleDto dto) {
        sampleService.deleteSample(dto);
        return ResponseEntity.ok(dto);
    }
}
