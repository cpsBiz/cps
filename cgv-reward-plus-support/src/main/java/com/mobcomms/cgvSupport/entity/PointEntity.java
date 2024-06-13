package com.mobcomms.cgvSupport.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "point")
@EntityListeners(AuditingEntityListener.class)
public class PointEntity {
    @Id
    @Column(name = "pk")
    private Long pk;

    @Column(name = "member_pk")
    private Integer memberPk;

    @Column(name = "category")
    private String category;

    @Column(name = "type")
    private String type;

    @Column(name = "point")
    private Integer point;

    @Column(name = "description")
    private String description;

    @Column(name = "mark")
    private String mark;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
