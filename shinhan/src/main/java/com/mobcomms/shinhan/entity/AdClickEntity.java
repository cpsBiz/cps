package com.mobcomms.shinhan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/*
 * Created by enliple
 * Create Date : 2024-07-02
 * Class 설명, method
 * UpdateDate : 2024-07-02, 업데이트 내용
 */
@Data
@Entity
@Table(name = "ckd_ad_click")
public class AdClickEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_click_seq", nullable = false)
    private Long adClickSeq;

    @Column(name = "user_key", nullable = false, length = 200)
    private String userKey;

    @Column(name = "ad_url", nullable = false, length = 500)
    private String adUrl;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @Column(name = "zone_id", nullable = false, length = 45)
    private String zoneId;

    @Column(name = "stats_dttm", nullable = false)
    private int statsDttm;

    @Column(name = "regdate", nullable = false)
    private LocalDateTime regDate;
}
