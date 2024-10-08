package com.cps.cpsService.entity;

import com.cps.cpsService.entity.pk.CpsGiftProductPk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_GIFT_PRODUCT")
@IdClass(CpsGiftProductPk.class)
public class CpsGiftProductEntity {
    @Id
    @Column(name = "BRAND_ID", updatable = false, nullable = false)
    private String brandId;

    @Id
    @Column(name = "PRODUCT_ID", updatable = false, nullable = false)
    private String productId;

    @Id
    @Column(name = "MERCHANT_ID", updatable = false, nullable = false)
    private String merchantId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_YN")
    private String productYn;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
