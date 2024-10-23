package com.cps.common.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsRewardUnitPk implements Serializable {
    @Column(name = "REWARD_UNIT_NUM")private int rewardUnitNum;
    @Column(name = "CLICK_NUM")private int clickNum;
    @Column(name = "REG_DAY")private int regDay;
}