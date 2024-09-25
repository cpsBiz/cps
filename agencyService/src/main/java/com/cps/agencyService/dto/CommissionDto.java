package com.cps.agencyService.dto;

import com.cps.agencyService.entity.CpsClickEntity;
import lombok.Data;

@Data
public class CommissionDto {
   private CpsClickEntity cpsClickEntity;
   private int memberCommissionShare;
   private int userCommissionShare;


   public CommissionDto(CpsClickEntity cpsClickEntity, int memberCommissionShare, int userCommissionShare) {
      this.cpsClickEntity = cpsClickEntity;
      this.memberCommissionShare = memberCommissionShare;
      this.userCommissionShare = userCommissionShare;
   }

}

