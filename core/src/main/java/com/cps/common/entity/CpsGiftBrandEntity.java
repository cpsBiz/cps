package com.cps.common.entity;

import com.cps.common.entity.pk.CpsGiftBrandPk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_GIFT_BRAND")
@IdClass(CpsGiftBrandPk.class)
public class CpsGiftBrandEntity {
    @Id
    @Column(name = "BRAND_ID", updatable = false, nullable = false)
    private String brandId;

    @Id
    @Column(name = "AFFLIATE_ID", updatable = false, nullable = false)
    private String affliateId;

    @Id
    @Column(name = "MERCHANT_ID", updatable = false, nullable = false)
    private String merchantId;

    @Id
    @Column(name = "BRAND_TYPE", updatable = false, nullable = false)
    private String brandType;

    @Column(name = "BRAND_NAME")
    private String brandName;

    @Column(name = "BRAND_LOGO")
    private String brandLogo;

    @Column(name = "MIN_CNT")
    private int minCnt;

    @Column(name = "BRAND_YN")
    private String brandYn;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
