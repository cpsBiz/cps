package com.cps.cpsApi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsMemberPk implements Serializable {
    @Column(name = "MANAGER_ID") private String managerId;
    @Column(name = "MEMBER_ID") private String memberId;
}
