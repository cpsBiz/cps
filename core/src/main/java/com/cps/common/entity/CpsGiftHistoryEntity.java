package com.cps.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_GIFT_HISTORY")
public class CpsGiftHistoryEntity {
    @Id
    @Column(name = "HISTORY_NUM")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyNum;

    @Column(name = "USER_ID", updatable = false, nullable = false)
    private String userId;

    @Column(name = "AFFLIATE_ID", updatable = false, nullable = false)
    private String affliateId;

    @Column(name = "MERCHANT_ID", updatable = false, nullable = false)
    private String merchantId;

    @Column(name = "BRAND_ID", updatable = false, nullable = false)
    private String brandId;

    @Column(name = "PRODUCT_ID", updatable = false, nullable = false)
    private String productId;

    @Column(name = "CNT")
    private int cnt;

    @Column(name = "AWARD_DAY")
    private int awardDay;

    @Column(name = "AWARD_YM")
    private int awardYm;

    @Column(name = "BARCODE")
    private String barcode;

    @Column(name = "PIN_NO")
    private String pinNo;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "TR_ID")
    private String trId;

    @Column(name = "USE_DAY")
    private int useDay;

    @Column(name = "VALID_DAY")
    private int validDay;

    @Column(name = "GIFT_YN")
    private String giftYn;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
