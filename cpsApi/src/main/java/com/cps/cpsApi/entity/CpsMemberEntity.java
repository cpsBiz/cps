package com.cps.cpsApi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_MEMBER")
public class CpsMemberEntity {
    @Id
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private String memberId;

    @Column(name = "MEMBER_PW")
    private String memberPw;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "BUSINESS_TYPE")
    private String businessType;

    @Column(name = "AGENCY_ID")
    private String agencyId;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "STATUS")
    private String status = "N";

    @Column(name = "CEO_NAME")
    private String ceoName;

    @Column(name = "BUSINESS_NUMBER")
    private String businessNumber;

    @Column(name = "COMPANY_ADDRESS")
    private String companyAddress;

    @Column(name = "BUSINESS_CATEGORY")
    private String businessCategory;

    @Column(name = "BUSINESS_SECTOR")
    private String buisinessSector;

    @Column(name = "MANAGER_NAME")
    private String managerName;

    @Column(name = "MANAGER_EMAIL")
    private String managerEmail;

    @Column(name = "MANAGER_PHONE")
    private String managerPhone;

    @Column(name = "COMPANY_PHONE")
    private String companyPhone;

    @Column(name = "LICENSE")
    private String license;

    @Column(name = "BRITH_YEAR")
    private String birthYear;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "LAST_LOGIN", updatable = false)
    private LocalDateTime lastLogin;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
