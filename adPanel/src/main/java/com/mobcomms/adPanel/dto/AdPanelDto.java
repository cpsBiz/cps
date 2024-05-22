package com.mobcomms.adPanel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobcomms.adPanel.entity.AdPanelEntity;

import java.time.LocalDateTime;

public record AdPanelDto(String clientCode, String productCode, String zoneId, String osType, String useYn, String wSize, String hSize, String viewIndex,
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
                        LocalDateTime regDttm,
                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
                        LocalDateTime modDttm,
                        String modUserKey) {

    public static AdPanelEntity toAdPanelEntity(AdPanelDto dto){
        return dto!=null ? new AdPanelEntity(dto.clientCode(),dto.productCode(),dto.zoneId(),dto.osType(),dto.useYn(),dto.wSize(),dto.hSize()
                                         ,dto.viewIndex(),dto.regDttm(),dto.modDttm(), dto.modUserKey()):null;
        }

    //list 불러오기 테스트
    public static AdPanelDto adPanelList(AdPanelEntity entity){
        return entity!=null ? new AdPanelDto(entity.getClientCode(),entity.getProductCode(),entity.getZoneId(),entity.getOsType(),entity.getUseYn(),entity.getWSize(),entity.getHSize()
                ,entity.getViewIndex(),entity.getRegDttm(),entity.getModDttm(), entity.getModUserKey()):null;
        }
    }
