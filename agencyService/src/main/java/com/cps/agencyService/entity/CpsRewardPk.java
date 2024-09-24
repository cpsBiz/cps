package com.cps.agencyService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Embeddable
public class CpsRewardPk implements Serializable {
    @Column(name = "CLICK_NUM")private int clickNum;
    @Column(name = "ORDER_NO")private String orderNo;
    @Column(name = "PRODUCT_CODE")private String productCode;
    @Column(name = "REG_DAY")private int regDay;
}