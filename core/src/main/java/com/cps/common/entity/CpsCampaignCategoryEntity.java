package com.cps.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_CAMPAIGN_CATEGORY")
public class CpsCampaignCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_NUM", updatable = false, nullable = false)
    private int categoryNum;

    @Column(name = "CAMPAIGN_NUM", updatable = false, nullable = false)
    private int campaignNum;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "RS")
    private int rs;

    @Column(name = "DENY_YN")
    private String denyYn;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
