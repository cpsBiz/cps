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
@Table(name = "ckd_user_info")
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "user_uuid", nullable = false, length = 45)
    private String userKey;

    @Column(name = "user_give_point", nullable = false)
    private long userGivePoint;

    @Column(name = "user_app_os", nullable = false, length = 45)
    private String userAppOs;

    @Column(name = "user_app_type", nullable = false, length = 45)
    private String userAppType;

    @Column(name = "user_agree_terms", nullable = false, length = 45)
    private String userAgreeTerms;

    @Column(name = "reg_dttm", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "alt_dttm", nullable = false)
    private LocalDateTime editDate;

    @Column(name = "adid", nullable = false, length = 45)
    private String adid;


}
