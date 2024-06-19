package com.mobcomms.raising.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameDto {
    private long gameSeq;
    private long userSeq;
    private long characterSeq;
    private long goodsSeq;
    private int point;
    private String endYn;
    private LocalDateTime playDate;
}

