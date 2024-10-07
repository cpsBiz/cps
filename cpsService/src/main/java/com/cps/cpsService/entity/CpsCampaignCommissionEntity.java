package com.cps.cpsService.entity;

import com.cps.cpsService.entity.pk.CpsCampaignCommissionPk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(CpsCampaignCommissionPk.class)
@Table(name = "CPS_CAMPAIGN_COMMISSION")
public class CpsCampaignCommissionEntity {
    @Id
    @Column(name = "CAMPAIGN_NUM", updatable = false, nullable = false)
    private int campaignNum;

    @Id
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private String memberId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MEMBER_COMMISSION_SHARE")
    private int memberCommissionShare;

    @Column(name = "USER_COMMISSION_SHARE")
    private int userCommissionShare;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
