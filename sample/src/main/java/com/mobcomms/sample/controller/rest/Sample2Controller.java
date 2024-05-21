package com.mobcomms.sample.controller.rest;

import com.mobcomms.sample.dto.Sample2Dto;
import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.service.Sample2Service;
import com.mobcomms.sample.service.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/2")
public class Sample2Controller {

    private final Sample2Service sample2Service;

    @GetMapping("")
    @Operation(summary = "읽기!", description = "select!")
    public ResponseEntity<List<Sample2Dto>> readSample() {
        return ResponseEntity.ok(sample2Service.readSample());
    }

    @PostMapping("")
    @Operation(summary = "쓰기!", description = "insert!")
    public ResponseEntity createSample(@RequestBody Sample2Dto dto) {
        return ResponseEntity.ok("쓰기! \n" + sample2Service.createSample(Sample2Dto.toEntity(dto)));
    }

    @PatchMapping("")
    @Operation(summary = "수정하기", description = "update!")
    public ResponseEntity updateSample(@RequestBody Sample2Dto dto) {
        return ResponseEntity.ok("수정하기! \n" + sample2Service.updateSample(Sample2Dto.toEntity(dto)));
    }

    @PutMapping("")
    @Operation(summary = "대체하기(전체내용수정)!", description = "delete & insert!")
    public ResponseEntity deleteInsertSample(@RequestBody Sample2Dto dto) throws Exception {
        return ResponseEntity.ok("대체하기(전체내용수정)! \n" + sample2Service.deleteInsertSample(Sample2Dto.toEntity(dto)));
    }

    @DeleteMapping("")
    @Operation(summary = "삭제하기!", description = "delete!")
    public ResponseEntity deleteSample(@RequestBody Sample2Dto dto) {
        sample2Service.deleteSample(Sample2Dto.toEntity(dto));
        return ResponseEntity.ok("삭제! \n" + dto);
    }
}
