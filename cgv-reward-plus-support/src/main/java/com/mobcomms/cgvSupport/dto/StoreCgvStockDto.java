package com.mobcomms.cgvSupport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobcomms.cgvSupport.entity.StoreCgvStockEntity;
import com.mobcomms.cgvSupport.enums.StateEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Schema(description = "유저적립카우트 DTO")
public record StoreCgvStockDto(
        @Schema(description = "고유키")
        Long pk,
        @Schema(description = "상품키")
        Integer goodsPk,
        @Schema(description = "쿠폰번호")
        String couponNo,
        @Schema(description = "가격")
        String price,
        @Schema(description = "만료일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        Date expireDate,
        @Schema(description = "상태")
        String state,
        @Schema(description = "등록일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime regDate,
        @Schema(description = "수정일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime modDate)
{
    public static StoreCgvStockEntity toEntity(StoreCgvStockDto dto) {
        return dto != null ? new StoreCgvStockEntity(
                dto.pk, dto.goodsPk, dto.couponNo, dto.price, dto.expireDate, StateEnum.fromCode(dto.state), dto.regDate, dto.modDate) : null;
    }

    public static StoreCgvStockDto of(StoreCgvStockEntity entity) {
        return  entity != null
                ? new StoreCgvStockDto(
                        entity.getPk(), entity.getGoodsPk(), entity.getCouponNo(), entity.getPrice(),
                        entity.getExpireDate(), entity.getState().getCode(), entity.getRegDate(), entity.getModDate()) : null;
    }
}
