package com.mobcomms.raising.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "roulette_character_level_point")
public class RouletteCharacterLevelPointEntity {
    @Id
    @Column(name = "character_level", nullable = false)
    private Integer id;

    @Column(name = "ratio_1", nullable = false)
    private Integer ratio1;

    @Column(name = "point_1", nullable = false)
    private Integer point1;

    @Lob
    @Column(name = "use_yn_1")
    private String useYn1;

    @Column(name = "ratio_2", nullable = false)
    private Integer ratio2;

    @Column(name = "point_2", nullable = false)
    private Integer point2;

    @Lob
    @Column(name = "use_yn_2")
    private String useYn2;

    @Column(name = "ratio_3", nullable = false)
    private Integer ratio3;

    @Column(name = "point_3", nullable = false)
    private Integer point3;

    @Lob
    @Column(name = "use_yn_3")
    private String useYn3;

    @Column(name = "ratio_4", nullable = false)
    private Integer ratio4;

    @Column(name = "point_4", nullable = false)
    private Integer point4;

    @Lob
    @Column(name = "use_yn_4")
    private String useYn4;

    @Column(name = "ratio_5", nullable = false)
    private Integer ratio5;

    @Column(name = "point_5", nullable = false)
    private Integer point5;

    @Lob
    @Column(name = "use_yn_5")
    private String useYn5;

    @Column(name = "ratio_6", nullable = false)
    private Integer ratio6;

    @Column(name = "point_6", nullable = false)
    private Integer point6;

    @Lob
    @Column(name = "use_yn_6")
    private String useYn6;

    @Column(name = "ratio_7", nullable = false)
    private Integer ratio7;

    @Column(name = "point_7", nullable = false)
    private Integer point7;

    @Lob
    @Column(name = "use_yn_7")
    private String useYn7;

    @Column(name = "ratio_8", nullable = false)
    private Integer ratio8;

    @Column(name = "point_8", nullable = false)
    private Integer point8;

    @Lob
    @Column(name = "use_yn_8")
    private String useYn8;

    @Column(name = "ratio_9", nullable = false)
    private Integer ratio9;

    @Column(name = "point_9", nullable = false)
    private Integer point9;

    @Lob
    @Column(name = "use_yn_9")
    private String useYn9;

    @Column(name = "ratio_10", nullable = false)
    private Integer ratio10;

    @Column(name = "point_10", nullable = false)
    private Integer point10;

    @Lob
    @Column(name = "use_yn_10")
    private String useYn10;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "reg_user", nullable = false, length = 20)
    private String regUser;

    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    @Column(name = "mod_user", nullable = false, length = 20)
    private String modUser;

}