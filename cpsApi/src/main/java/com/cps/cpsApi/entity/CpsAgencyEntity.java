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
@Table(name = "CPS_AGENCY")
@IdClass(CpsAgencyPk.class)
public class CpsAgencyEntity {
    @Id
    @Column(name = "AGENCY_ID", updatable = false, nullable = false)
    private String agencyId;

    @Id
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private String memberId;


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

    @Column(name = "CLICK_URL")
    private String clickUrl;

    @Column(name = "WHEN_TRANS")
    private String whenTrans;

    @Column(name = "TRANS_REPOSITION")
    private String transReposition;

    @Column(name = "DENY_AD")
    private String denyAd;

    @Column(name = "DENY_PRODUCT")
    private String denyProduct;

    @Column(name = "NOTICE")
    private String notice;

    @Column(name = "COMMISSION_PAYMENT_STANDARD")
    private String commissionPaymentStandard;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
