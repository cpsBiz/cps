package com.cps.cpsApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpsMemberListDto {
    String memberId;
    String memberPw;
    String memberName;
    String type;
    String managerName; //담당자명
    String managerEmail;//담당자 email
    String managerPhone;//담당자 전화번호
    String companyPhone;//대표전화
}

