package com.cps.cpsService.entity;

import com.cps.cpsService.entity.pk.CpsCampaignFavoritesPk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(CpsCampaignFavoritesPk.class)
@Table(name = "CPS_CAMPAIGN_FAVORITES")
public class CpsCampaignFavoritesEntity {
    @Id
    @Column(name = "CAMPAIGN_NUM", updatable = false, nullable = false)
    private int campaignNum;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;
}
