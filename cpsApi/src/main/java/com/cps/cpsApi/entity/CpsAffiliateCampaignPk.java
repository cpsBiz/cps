package com.cps.cpsApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsAffiliateCampaignPk implements Serializable {
    @Column(name = "AFFILIATE_ID") private String affiliateId;
    @Column(name = "MEMBER_ID") private String memberId;
    @Column(name = "CAMPAIGN_NUM") private int campaignNum;
}
