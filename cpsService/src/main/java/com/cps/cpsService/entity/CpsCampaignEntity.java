package com.cps.cpsService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_CAMPAIGN")
public class CpsCampaignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAMPAIGN_NUM", updatable = false, nullable = false)
    private int campaignNum;

    @Column(name = "MERCHANT_ID", updatable = false, nullable = false)
    private String merchantId;

    @Column(name = "ADMIN_ID", updatable = false, nullable = false)
    private String adminId;

    @Column(name = "CAMPAIGN_NAME")
    private String campaignName;

    @Column(name = "CAMPAIGN_START")
    private String campaignStart;

    @Column(name = "CAMPAIGN_END")
    private String campaignEnd;

    @Column(name = "URL")
    private String url;

    @Column(name = "CLICK_URL")
    private String clickUrl;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "CAMPAIGN_AUTO")
    private String campaignAuto = "A";

    @Column(name = "REWARD_YN")
    private String rewardYn = "N";

    @Column(name = "PC_YN")
    private String pcYn = "N";

    @Column(name = "MOBILE_YN")
    private String mobileYn = "N";

    @Column(name = "AOS_YN")
    private String aosYn = "N";

    @Column(name = "IOS_YN")
    private String iosYn = "N";

    @Column(name = "RETURN_DAY")
    private int returnDay = 0;

    @Column(name = "COMMISSION_SEND_YN")
    private String commissionSendYn = "N";

    @Column(name = "WHEN_TRANS")
    private String whenTrans;

    @Column(name = "TRANS_REPOSITION")
    private String transReposition;

    @Column(name = "COMMISSION_PAYMENT_STANDARD")
    private String commissionPaymentStandard;

    @Column(name = "DENY_AD")
    private String denyAd;

    @Column(name = "DENY_PRODUCT")
    private String denyProduct;

    @Column(name = "NOTICE")
    private String notice;

    @Column(name = "CAMPAIGN_STATUS")
    private String campaignStatus = "N";

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
