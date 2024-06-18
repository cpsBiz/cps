package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "`character`")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_seq", nullable = false)
    private Long id;

    @Column(name = "character_name", nullable = false, length = 20)
    private String characterName;

    @Column(name = "character_img", nullable = false)
    private String characterImg;

    @Column(name = "max_level", nullable = false)
    private Integer maxLevel;

    @Column(name = "first_view_yn")
    private Character firstViewYn;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

    @Column(name = "mod_user", nullable = false)
    private Long modUser;

}