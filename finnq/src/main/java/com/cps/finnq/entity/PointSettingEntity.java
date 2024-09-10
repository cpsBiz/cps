package com.cps.finnq.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "api_point_setting")
public class PointSettingEntity {
    @Id
    @Column(name = "type")
    private int type;

    @Column(name = "name")
    private String name;

    @Column(name = "point")
    private int point;

    @Column(name = "mod_date")
    private String modDate;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "unit")
    private String unit;

    @Column(name = "useYN")
    private String useYN;
}
