package com.mobcomms.hanapay.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ckd_user_info")
public class UserEntity {
    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIdx;

    @Column(name = "user_uuid")
    private String userUuid;

    @Column(name = "user_give_point")
    private String userGivePoint;

    @Column(name = "user_app_os")
    private String userAppOs;

    @Column(name = "user_app_type")
    private String userAppType;

    @Column(name = "user_adid")
    private String userAdid;

    @Column(name = "user_agree_terms")
    private String userAgreeTerms;

    @CreatedDate
    @Column(name = "reg_dttm")
    private LocalDateTime regDttm;

    @LastModifiedDate
    @Column(name = "alt_dttm")
    private LocalDateTime altDttm;
}
