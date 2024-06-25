package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "user_game")
public class UserGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_game_seq", nullable = false)
    private Long id;

    @Column(name = "user_seq", nullable = false)
    private Long userSeq;

    @Column(name = "character_seq", nullable = false)
    private Long characterSeq;

    @Column(name = "goods_seq", nullable = false)
    private Long goodsSeq;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Lob
    @Column(name = "end_yn")
    private String endYn;

    @Column(name = "play_date", nullable = false)
    private Instant playDate;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

}