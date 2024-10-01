package com.cps.cpsApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsMemberSitePk implements Serializable {
    @Column(name = "MEMBER_ID") private String memberId;
    @Column(name = "SITE") private String site;
}
