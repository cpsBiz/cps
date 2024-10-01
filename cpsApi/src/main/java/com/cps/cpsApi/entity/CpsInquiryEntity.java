package com.cps.cpsApi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(CpsInquiryPk.class)
@Table(name = "CPS_INQUIRY")
public class CpsInquiryEntity {
    @Id
    @Column(name = "INQUIRY_NUM", updatable = false, nullable = false)
    private int inquiryNum;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "INQUIRY_TYPE")
    private String inquiryType;

    @Column(name = "CAMPAIGN_NUM")
    private int campaignNum;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "REG_DAY")
    private int regDay;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "PAYMENT")
    private String payment;

    @Column(name = "PRODUCT_PRICE")
    private int productPrice;

    @Column(name = "PRODUCT_CNT")
    private int productCnt;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "INFORMATION")
    private String information;

    @Column(name = "ANSWER_YN")
    private String answerYn;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
