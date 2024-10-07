package com.cps.cpsService.entity;

import com.cps.cpsService.entity.pk.CpsMemberSitePk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(CpsMemberSitePk.class)
@Table(name = "CPS_MEMBER_SITE")
public class CpsMemberSiteEntity {
    @Id
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private String memberId;

    @Id
    @Column(name = "SITE")
    private String site;

    @Column(name = "SITE_NAME")
    private String siteName;

    @Column(name = "CATEGORY")
    private String category;
}
