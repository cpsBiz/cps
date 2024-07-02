package com.mobcomms.raising.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mobcomms.raising.entity.MissionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


/*
 * Created by shchoi3
 * Create Date : 2024-06-20
 * class 설명, method
 * UpdateDate : 2024-06-20, 업데이트 내용
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MissionDto {
    private MissionA missionA;
    private MissionB missionB;
    private MissionC missionC;
}




