package com.cps.cpsService.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsCampaignCommissionPk implements Serializable {
    @Column(name = "CAMPAIGN_NUM") private int campaignNum;
    @Column(name = "AFFLIATE_ID") private String affliateId;
}
