package com.cps.viewService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(CpsGiftBrandPk.class)
@Table(name = "CPS_GIFT_BRAND")
public class CpsGiftBradnEntity {
    @Id
    @Column(name = "BRAND_ID", updatable = false, nullable = false)
    private String brandId;

    @Id
    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "BRAND_NAME")
    private String brandName;

    @Id
    @Column(name = "MIN_CNT")
    private int minCnt;

    @Id
    @Column(name = "BRAND_YN")
    private String brandYn;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @CreatedDate
    @Column(name = "MOD_DATE", updatable = false)
    private LocalDateTime modDate;
}
