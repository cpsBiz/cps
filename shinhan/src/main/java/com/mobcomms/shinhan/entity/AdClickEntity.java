package com.mobcomms.shinhan.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
 * Created by enliple
 * Create Date : 2024-07-02
 * Class 설명, method
 * UpdateDate : 2024-07-02, 업데이트 내용
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ckd_ad_click")
public class AdClickEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_click_seq", nullable = false)
    private Long adClickSeq;

    @Column(name = "stats_dttm", nullable = false)
    private int statsDttm;

    @Column(name = "user_key", nullable = false, length = 45)
    private String userKey;

    @Column(name = "zone_id", nullable = false, length = 45)
    private String zoneId;

    @Column(name = "ad_url", nullable = false, length = 500)
    private String adUrl;

    @Column(name = "ip_address", nullable = false, length = 30)
    private String ipAddress;

    @CreatedDate
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;
}
