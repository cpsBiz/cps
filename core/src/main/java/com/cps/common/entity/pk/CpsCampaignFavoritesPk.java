package com.cps.common.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsCampaignFavoritesPk implements Serializable {
    @Column(name = "CAMPAIGN_NUM") private int campaignNum;
    @Column(name = "USER_ID") private String userId;
    @Column(name = "AFFLIATE_ID") private String affliateId;
}