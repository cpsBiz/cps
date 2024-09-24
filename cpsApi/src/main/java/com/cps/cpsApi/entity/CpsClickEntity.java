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
@Table(name = "CPS_CLICK")
public class CpsClickEntity {
    @Id
    @Column(name = "CLICK_NUM")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clickNum;

    @Column(name = "REG_DAY")
    private int regDay;

    @Column(name = "REG_YM")
    private int regYm;

    @Column(name = "REG_HOUR")
    private String regHour;

    @Column(name = "CAMPAIGN_NUM")
    private String campaignNum;

    @Column(name = "AFFLIATE_ID")
    private String affliateId;

    @Column(name = "ZONE_ID")
    private String zoneId;

    @Column(name = "AGENCY_ID")
    private String agencyId;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SITE")
    private String site;

    @Column(name = "OS")
    private String os;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ADID")
    private String adId;

    @Column(name = "REWARD_YN")
    private String rewardYn = "N";

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @CreatedDate
    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
