package com.cps.cpsApi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CpsMemberListDto {
    String memberId;
    String managerId;
    String companyName;
    String type;
    String status;
    String managerName;
    String officePhone;
    String phone;
    String mail;
    String address;
    String buisnessNumber;
    String category;
    String rewardYn;
    String mobileYn;
    int returnDay;
    String url;
    String logo;
}

