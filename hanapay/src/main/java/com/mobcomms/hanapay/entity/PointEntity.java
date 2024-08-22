package com.mobcomms.hanapay.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "api_point")
public class PointEntity {
    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pointId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "box")
    private String box;

    @Column(name = "reg_date_num")
    private String regDateNum;

    @Column(name = "code")
    private String code;

    @Column(name = "res")
    private String res;

    @Column(name = "type")
    private String type;

    @Column(name = "zone")
    private String zone;

    @Column(name = "point")
    private String point;

    @Column(name = "unique_insert")
    private String uniqueInsert;

    @Column(name = "ip_address")
    private String ipAddress;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private LocalDateTime modDate;
}
