package com.cps.cpsService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_VIEW")
public class CpsViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIEW_NUM", updatable = false, nullable = false)
    private int viewNum;

    @Column(name = "REG_DAY", updatable = false, nullable = false)
    private int regDay;

    @Column(name = "REG_HOUR")
    private String regHour;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "AGENCY_ID")
    private String agencyId;

    @Column(name = "CAMPAIGN_NUM")
    private int campaignNum;

    @Column(name = "CAMPAIGN_NAME")
    private String campaignName;

    @Column(name = "AFFLIATE_ID")
    private String affliateId;

    @Column(name = "ZONE_ID")
    private String zoneId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SITE")
    private String site;

    @Column(name = "CLICK_URL")
    private String clickUrl;

    @Column(name = "OS")
    private String os;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ADID")
    private String adId;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;
}
