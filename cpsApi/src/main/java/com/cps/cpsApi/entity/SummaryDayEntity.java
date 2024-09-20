package com.cps.cpsApi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "SUMMARY_DAY")
public class SummaryDayEntity {
    @Id
    @Column(name = "REG_DAY", updatable = false, nullable = false)
    private int regDay;

    @Id
    @Column(name = "REG_YM", updatable = false, nullable = false)
    private int regYm;

    @Id
    @JsonIgnore
    @Column(name = "CAMPAIGN_NUM", updatable = false, nullable = false)
    private int campaignNum;

    @Id
    @JsonIgnore
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private String memberId;

    @Id
    @Column(name = "AGENCY_ID", updatable = false, nullable = false)
    private String agencyId;

    @Id
    @Column(name = "AFFLIATE_ID")
    private String affliateId;

    @Id
    @Column(name = "ZONE_ID")
    private String zoneId;

    @Id
    @Column(name = "SITE")
    private String site;

    @Id
    @Column(name = "OS")
    private String os;

    @Column(name = "CNT")
    private int cnt;

    @Column(name = "CLICK_CNT")
    private int clickCnt;

    @Column(name = "CAMPAIGN_NAME")
    private String campaignName;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "AGENCY_NAME")
    private String agencyName;

    @Column(name = "AFFLIATE_NAME")
    private String affliateName;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
