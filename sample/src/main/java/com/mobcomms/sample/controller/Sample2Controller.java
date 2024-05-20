package com.mobcomms.sample.controller;

import com.mobcomms.sample.dto.Sample2Dto;
import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.service.Sample2Service;
import com.mobcomms.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/2")
public class Sample2Controller {

    private final Sample2Service sample2Service;

    @GetMapping("/")
    public ResponseEntity readSample() {
        return ResponseEntity.ok("읽기! \n" + sample2Service.readSample());
    }

    @PostMapping("/")
    public ResponseEntity createSample(@RequestBody Sample2Dto dto) {
        System.out.println("dto : " + dto);
        return ResponseEntity.ok("쓰기! \n" + sample2Service.createSample(Sample2Dto.toEntity(dto)));
    }

    @PatchMapping("/")
    public ResponseEntity updateSample(@RequestBody Sample2Dto dto) {
        return ResponseEntity.ok("수정하기! \n" + sample2Service.updateSample(Sample2Dto.toEntity(dto)));
    }

    @PutMapping("/")
    public ResponseEntity deleteInsertSample(@RequestBody Sample2Dto dto) throws Exception {
        return ResponseEntity.ok("대체하기(전체내용수정)! \n" + sample2Service.deleteInsertSample(Sample2Dto.toEntity(dto)));
    }

    @DeleteMapping("/")
    public ResponseEntity deleteSample(@RequestBody Sample2Dto dto) {
        sample2Service.deleteSample(Sample2Dto.toEntity(dto));
        return ResponseEntity.ok("삭제! \n" + dto);
    }
}
