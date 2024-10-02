package com.cps.cpsApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsGiftProductPk implements Serializable {
    @Column(name = "BRAND_ID") private String brandId;
    @Column(name = "PRODUCT_ID") private String productId;
    @Column(name = "MEMBER_ID") private String memberId;
}
