package com.cps.cpsApi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CPS_CATEGORY")
public class CpsCategoryEntity {
    @Id
    @Column(name = "CATEGORY", updatable = false, nullable = false)
    private String category;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "CATEGORY_RANK")
    private int categoryRank;

    @CreatedDate
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "MOD_DATE")
    private LocalDateTime modDate;
}
