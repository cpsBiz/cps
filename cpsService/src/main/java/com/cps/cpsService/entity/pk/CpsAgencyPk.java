package com.cps.cpsService.entity.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CpsAgencyPk implements Serializable {
    @Column(name = "AGENCY_ID") private String agencyId;
    @Column(name = "MEMBER_ID") private String memberId;
}
