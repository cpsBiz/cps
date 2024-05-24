package com.mobcomms.adPanel.model;

import lombok.Data;

@Data
public class AdPanelModel {
    private String adId;
    private String os;
    private String zoneId;
    private String width;
    private String height;

    //파라미터명 소문자로 요청
    public void setAdid(String value){
        this.adId = value;
    }
}
