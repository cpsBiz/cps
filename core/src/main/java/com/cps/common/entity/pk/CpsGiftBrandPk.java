package com.cps.common.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsGiftBrandPk implements Serializable {
    @Column(name = "BRAND_ID") private String brandId;
    @Column(name = "AFFLIATE_ID") private String affliateId;
    @Column(name = "MERCHANT_ID") private String merchantId;
    @Column(name = "BRAND_TYPE") private String brandType;
}