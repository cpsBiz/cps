package com.mobcomms.sample.controller;

import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1")
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/")
    public ResponseEntity readSample() {
        return ResponseEntity.ok("읽기! \n" + sampleService.readSample());
    }

    @PostMapping("/")
    public ResponseEntity createSample(@RequestBody SampleDto dto) {
        return ResponseEntity.ok("쓰기! \n" + sampleService.createSample(SampleDto.toEntity(dto)));
    }

    @PatchMapping("/")
    public ResponseEntity updateSample(@RequestBody SampleDto dto) {
        return ResponseEntity.ok("수정하기! \n" + sampleService.updateSample(SampleDto.toEntity(dto)));
    }

    @PutMapping("/")
    public ResponseEntity deleteInsertSample(@RequestBody SampleDto dto) throws Exception {
        return ResponseEntity.ok("대체하기(전체내용수정)! \n" + sampleService.deleteInsertSample(SampleDto.toEntity(dto)));
    }

    @DeleteMapping("/")
    public ResponseEntity deleteSample(@RequestBody SampleDto dto) {
        sampleService.deleteSample(SampleDto.toEntity(dto));
        return ResponseEntity.ok("삭제! \n" + dto);
    }
}
