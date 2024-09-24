package com.cps.cpsApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsRewardPk implements Serializable {
    @Column(name = "CLICK_NUM")private int clickNum;
    @Column(name = "ORDER_NO")private String orderNo;
    @Column(name = "PRODUCT_CODE")private String productCode;
    @Column(name = "REG_DAY")private int regDay;
}