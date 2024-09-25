package com.cps.agencyService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsCampaignCommissionPk implements Serializable {
    @Column(name = "AFFILIATE_ID") private String affiliateId;
    @Column(name = "CAMPAIGN_NUM") private int campaignNum;
}
