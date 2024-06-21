package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "mission_item")
public class MissionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_item_seq", nullable = false)
    private Long id;

    @Column(name = "mission_item_name", nullable = false, length = 55)
    private String missionItemName;

    @Column(name = "gain_count", nullable = false)
    private Integer gainCount;

    @Column(name = "landing_url", nullable = false)
    private String landingUrl;

    @Lob
    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "reg_date", nullable = false)
    private Instant regDate;

    @Column(name = "reg_user", nullable = false)
    private Long regUser;

    @Column(name = "mod_date", nullable = false)
    private Instant modDate;

    @Column(name = "mod_user", nullable = false)
    private Long modUser;

}