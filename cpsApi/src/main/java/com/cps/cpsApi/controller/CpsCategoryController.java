package com.cps.cpsApi.controller;

import com.cps.common.constant.Constant;
import com.cps.cpsApi.packet.CategoryPacket;
import com.cps.cpsApi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CpsCategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 등록
     *
     * @date 2024-09-09
     */
    @Operation(summary = "카테고리 등록, 수정, 삭제")
    @PostMapping(value = "/category")
    public ResponseEntity<CategoryPacket.CategoryInfo.Response> category(@Valid @RequestBody CategoryPacket.CategoryInfo.CategoryRequest request) throws Exception {
        var result = new CategoryPacket.CategoryInfo.Response();

        try {
            var category = categoryService.category(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(category.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(category.getResultCode(), category.getResultMessage());
            }
            result.setData(category.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("category Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}