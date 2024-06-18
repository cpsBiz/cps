package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "goods_pin")
public class GoodsPinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pin_seq", nullable = false)
    private Long id;

    @Column(name = "pin", nullable = false, length = 55)
    private String pin;

    @Column(name = "goods_seq", nullable = false)
    private Long goodsSeq;

    @Column(name = "expired_day")
    private Integer expiredDay;

    @Column(name = "pin_user_yn")
    private Character pinUserYn;

    @Column(name = "use_yn")
    private Character useYn;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

    @Column(name = "mod_user", nullable = false)
    private Long modUser;

}