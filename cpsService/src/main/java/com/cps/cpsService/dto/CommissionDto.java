package com.cps.cpsService.dto;

import com.cps.cpsService.entity.CpsClickEntity;
import lombok.Data;

@Data
public class CommissionDto {
   private CpsClickEntity cpsClickEntity;
   private int memberCommissionShare;
   private int userCommissionShare;
   private String memberName;


   public CommissionDto(CpsClickEntity cpsClickEntity, int memberCommissionShare, int userCommissionShare, String memberName) {
      this.cpsClickEntity = cpsClickEntity;
      this.memberCommissionShare = memberCommissionShare;
      this.userCommissionShare = userCommissionShare;
      this.memberName = memberName;
   }

}

