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
@IdClass(CpsMemberPk.class)
public class CpsMemberEntity {
    @Id
    @Column(name = "MANAGER_ID", updatable = false, nullable = false)
    private String managerId = "ENLIPLE";

    @Id
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private String memberId;

    @Column(name = "MEMBER_PW")
    private String memberPw;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "STATUS")
    private String status = "N";

    @Column(name = "MANAGER_NAME")
    private String managerName;

    @Column(name = "OFFICE_PHONE")
    private String officePhone;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "BUSINESS_NUMBER")
    private String buisnessNumber;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "REWARD_YN")
    private String rewardYn;

    @Column(name = "MOBILE_YN")
    private String mobileYn;

    @Column(name = "RETURN_DAY")
    private int returnDay;

    @Column(name = "URL")
    private String url;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "LAST_LOGIN", updatable = false)
    private LocalDateTime lastLogin;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
