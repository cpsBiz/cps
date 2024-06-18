package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "mission_user_character_growth_history")
public class MissionUserCharacterGrowthHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_user_character_growth_history_seq", nullable = false)
    private Long id;

    @Column(name = "mission_history_seq", nullable = false)
    private Long missionHistorySeq;

    @Column(name = "exp", nullable = false)
    private Integer exp;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

}