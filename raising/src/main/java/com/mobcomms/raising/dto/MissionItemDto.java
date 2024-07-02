package com.mobcomms.raising.dto;

import lombok.Data;

/*
 * Created by enliple
 * Create Date : 2024-06-19
 * MissionDto
 * UpdateDate : 2024-06-19
 */
@Data
public class MissionItemDto {
    private int missionSeq;
    private String missionItemName;
    private Integer gainCount;
    private String landingUrl;
    private Character useYn;
    private Long regUser;
}
