package com.cps.cpsService.entity;

import com.cps.cpsService.entity.pk.CpsRewardUnitPk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_REWARD_UNIT")
@IdClass(CpsRewardUnitPk.class)
public class CpsRewardUnitEntity {
    @Id
    @Column(name = "CLICK_NUM")
    private int clickNum;

    @Id
    @Column(name = "REG_DAY", updatable = false)
    private int regDay;

    @Column(name = "REG_YM", updatable = false)
    private int regYm;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MERCHANT_ID")
    private String merchantId;

    @Column(name = "AFFLIATE_ID")
    private String affliateId;

    @Column(name = "TOTAL_PRICE")
    private long totalPrice;

    @Column(name = "PRODUCT_NAME", updatable = false)
    private String productName;

    @Column(name = "REWARD_CNT")
    private long rewardCnt;

    @Column(name = "CNT")
    private long cnt;

    @Column(name = "STOCK_CNT")
    private int stockCnt;

    @Column(name = "STATUS")
    private int status;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
