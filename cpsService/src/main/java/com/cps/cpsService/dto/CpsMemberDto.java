package com.cps.cpsService.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class CpsMemberDto {
    String memberId;
    String memberName;
    String type;
    String status;

    List<CpsMemberSite> siteList;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CpsMemberSite {
       String site;
       String siteName;
       String category;
    }
}

