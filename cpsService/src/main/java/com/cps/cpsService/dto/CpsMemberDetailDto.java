package com.cps.cpsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsMemberDetailDto {
    private String memberId;
    private String memberPw;
    private String memberName;
    private String type;
    private String businessType;
    private String agencyId;
    private String bank;
    private String accountName;
    private String status;
    private String ceoName;
    private String businessNumber;
    private String companyAddress;
    private String businessCategory;
    private String businessSector;
    private String managerName;
    private String managerEmail;
    private String managerPhone;
    private String companyPhone;
    private String birthYear;
    private String sex;
    private String license;
    private String apiType;

    List<CpsMemberSite> siteList;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class CpsMemberSite {
        String site;
        String siteName;
        String category;
    }
}

