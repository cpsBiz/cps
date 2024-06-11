package com.mobcomms.cgvSupport.controller;

import com.mobcomms.cgvSupport.dto.AccumulationUserCountDto;
import com.mobcomms.cgvSupport.service.PoinstService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointController {

    private final PoinstService poinstService;

    @GetMapping("/point/user/cnt")
    public ResponseEntity<List<AccumulationUserCountDto>> readAccumulationUserCount(
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return ResponseEntity.ok(
                poinstService.readAccumulationUserCount(startDate, endDate).stream()
                        .map(map -> AccumulationUserCountDto.of(map))
                        .collect(Collectors.toList()));
    }
}
