package com.mobcomms.shinhan.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ckd_api_point")
public class PointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_seq", nullable = false)
    private Long pointSeq;

    @Column(name = "stats_dttm", nullable = false)
    private int statsDttm;

    @Column(name = "user_key", nullable = false, length = 45)
    private String userKey;

    @Column(name = "code", nullable = false, length = 6)
    private String code;

    @Column(name = "shinhan_code", nullable = false, length = 50)
    private String shinhanCode;

    @Column(name = "point", nullable = false)
    private int point;

    @Column(name = "zone_id", nullable = false, length = 50)
    private String zoneId;

    @Column(name = "os", nullable = false, length = 3)
    private String os;

    @Column(name = "ad_url", nullable = false, length = 500)
    private String adUrl;

    @Column(name = "ip_address", nullable = false, length = 30)
    private String ipAddress;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @CreatedDate
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;
}
