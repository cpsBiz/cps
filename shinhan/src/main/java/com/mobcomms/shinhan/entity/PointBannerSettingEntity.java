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
@Table(name = "ckd_banner_set")
public class PointBannerSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type", nullable = false)
    private Long pointBannerSettingSeq;

    @Lob
    @Column(name = "useYN")
    private String useYn;

    @Column(name = "img", nullable = false, length = 200)
    private String img;

    @Column(name = "font_color", nullable = false, length = 45)
    private String fontColor;

    @Column(name = "unit", nullable = false, length = 45)
    private String unit;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "max_point", nullable = false)
    private Integer maxPoint;

    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "edit_date", nullable = false)
    private LocalDateTime editDate;
}
