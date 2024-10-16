package com.cps.cpsService.entity;

import com.cps.cpsService.entity.pk.CpsProductPk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_GIFT")
@IdClass(CpsProductPk.class)
public class CpsGiftEntity {
    @Id
    @Column(name = "BRAND_ID", updatable = false, nullable = false)
    private String brandId;

    @Id
    @Column(name = "PRODUCT_ID", updatable = false, nullable = false)
    private String productId;

    @Column(name = "BRAND_NAME")
    private String brandName;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_IMAGE_S")
    private String productImageS;

    @Column(name = "PRODUCT_IMAGE_L")
    private String productImageL;

    @Column(name = "BRAND_ICON")
    private String brandIcon;

    @Column(name = "REAL_PRICE")
    private int realPrice;

    @Column(name = "SALE_PRICE")
    private int salePrice;

    @Column(name = "DISCOUNT_RATE")
    private String discountRate;

    @Column(name = "DISCOUNT_PRICE")
    private int discountPrice;

    @Column(name = "LIMIT_DAY")
    private int limitDay;

    @Column(name = "VALID_DAY")
    private int validDay;

    @Column(name = "END_DAY")
    private int endDay;

    @Column(name = "CONTENT")
    private String content;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
