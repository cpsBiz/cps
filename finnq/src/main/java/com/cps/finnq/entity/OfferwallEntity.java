package com.cps.finnq.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "api_offerwall")
public class OfferwallEntity {
    @Id
    @Column(name = "offerwall_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offerwallId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reg_date_num")
    private String regDateNum;

    @Column(name = "ad_id")
    private String adId;

    @Column(name = "code")
    private String code;

    @Column(name = "res")
    private String res;

    @Column(name = "media")
    private String media;

    @Column(name = "ad_name")
    private String adName;

    @Column(name = "ad_type")
    private String adType;

    @Column(name = "point")
    private int point;

    @Column(name = "exchange")
    private double exchange;

    @Column(name = "profit")
    private double profit;

    @Column(name = "ip_address")
    private String ipAddress;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private LocalDateTime modDate;
}
