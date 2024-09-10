package com.cps.agencyService.entity;

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
public class ClickEntity {
    @Id
    @Column(name = "CLICK_NUM")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clickNum;

    @Column(name = "DATE_NUM")
    private String dateNum;

    @Column(name = "CAMPAIGN_ID")
    private String campaignId;

    @Column(name = "AFFLIATE_ID")
    private String affliateId;

    @Column(name = "ZONE_ID")
    private String zoneId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CODE")
    private String code = "4000";

    @Column(name = "MESSAGE")
    private String message = "클릭 캠페인 등록 오류";

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "REWARD_YN")
    private String rewardYn = "N";

    @CreatedDate
    @Column(name = "REG_DATE")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
