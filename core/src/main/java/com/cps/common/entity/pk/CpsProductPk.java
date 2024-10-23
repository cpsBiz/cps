package com.cps.common.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsProductPk implements Serializable {
    @Column(name = "BRAND_ID") private String brandId;
    @Column(name = "PRODUCT_ID") private String productId;
}
