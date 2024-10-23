package com.cps.common.entity;

import com.cps.common.entity.pk.CpsRewardFirstPk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_REWARD_FIRST")
@IdClass(CpsRewardFirstPk.class)
public class CpsRewardFirstEntity {
    @Id
    @Column(name = "CLICK_NUM")
    private int clickNum;

    @Id
    @Column(name = "ORDER_NO")
    private String orderNo;

    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Id
    @Column(name = "REG_DAY", updatable = false)
    private int regDay;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "REG_YM", updatable = false)
    private int regYm;

    @Column(name = "REG_HOUR", updatable = false)
    private String regHour;

    @Column(name = "MERCHANT_ID")
    private String merchantId;

    @Column(name = "AGENCY_ID")
    private String agencyId;

    @Column(name = "CAMPAIGN_NUM")
    private int campaignNum;

    @Column(name = "AFFLIATE_ID")
    private String affliateId;

    @Column(name = "ZONE_ID")
    private String zoneId;

    @Column(name = "SITE")
    private String site;

    @Column(name = "OS")
    private String os;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ADID")
    private String adId;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_CNT")
    private int productCnt;

    @Column(name = "PRODUCT_PRICE")
    private int productPrice;

    @Column(name = "TRANS_COMMENT")
    private String transComment;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "COMMISSION")
    private int commission;

    @Column(name = "COMMISSION_PROFIT")
    private int commissionProfit;

    @Column(name = "AFFLIATE_COMMISSION")
    private int affliateCommission;

    @Column(name = "USER_COMMISSION")
    private int userCommission;

    @Column(name = "COMMISSION_RATE")
    private String commissionRate;

    @Column(name = "BASE_COMMISSION")
    private String baseCommission;

    @Column(name = "INCENTIVE_COMMISSION")
    private String incentiveCommission;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
