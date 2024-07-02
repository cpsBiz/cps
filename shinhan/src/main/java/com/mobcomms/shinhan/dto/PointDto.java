package com.mobcomms.shinhan.dto;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
@Data
public class PointDto {
    private Long pointSeq;
    private String userKey;
    private String status;
    private String zoneId;
    private String adUrl;
    private String os;
    private String transactionId;
    private LocalDateTime regDate;
}
