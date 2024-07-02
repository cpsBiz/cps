package com.mobcomms.shinhan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
@Data
@Entity
@Table(name = "ckd_api_point")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_seq", nullable = false)
    private Long pointSeq;

    @Column(name = "user_key", nullable = false, length = 200)
    private String userKey;

    @Column(name = "status", nullable = false, length = 45)
    private String status;

    @Column(name = "zone_id", nullable = false, length = 45)
    private String zoneId;

    @Column(name = "os", nullable = false, length = 45)
    private String os;

    @Column(name = "transaction_id", nullable = false, length = 200)
    private String transactionId;

    @Column(name = "ad_url", nullable = false, length = 500)
    private String adUrl;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "stats_dttm", nullable = false)
    private int statsDttm;

    @Column(name = "point", nullable = false)
    private int point;

}
