package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "mission_user_history")
public class MissionUserHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_history_seq", nullable = false)
    private Long id;

    @Column(name = "mission_seq", nullable = false)
    private Long missionSeq;

    @Column(name = "mission_item_seq", nullable = false)
    private Long missionItemSeq;

    @Column(name = "user_seq", nullable = false)
    private Long userSeq;

    @Column(name = "game_seq", nullable = false)
    private Long gameSeq;

    @Column(name = "complated_yn")
    private Character complatedYn;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

    @Column(name = "mod_user", nullable = false)
    private Long modUser;

}