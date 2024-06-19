package com.mobcomms.raising.model;

import com.mobcomms.raising.entity.UserGameEntity;
import lombok.Data;
import org.joda.time.DateTime;

import java.time.LocalDateTime;

@Data
public class GameModel {
    private long gameSeq;
    private long userSeq;
    private long characterSeq;
    private long goodsSeq;
    private int point;
    private String endYn;
    private LocalDateTime playDate;
}

