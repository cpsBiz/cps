package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "character_history")
public class CharacterHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_seq", nullable = false)
    private Long id;

    @Column(name = "character_seq", nullable = false)
    private Long characterSeq;

    @Column(name = "character_name", nullable = false, length = 20)
    private String characterName;

    @Column(name = "max_level", nullable = false)
    private Integer maxLevel;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

}