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
@IdClass(CpsAffiliateCampaignPk.class)
@Table(name = "CPS_AFFILIATE_CAMPAIGN")
public class CpsAffilateCampaignEntity {
    @Id
    @Column(name = "AFFILIATE_ID", updatable = false, nullable = false)
    private String affiliateId;

    @Id
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private String memberId;

    @Id
    @Column(name = "CAMPAIGN_NUM", updatable = false, nullable = false)
    private int campaignNum;

    @Column(name = "STATUS")
    private String status;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
