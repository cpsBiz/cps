package com.cps.cpsService.dto;

import com.cps.cpsService.entity.CpsClickEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommissionDto {
   private CpsClickEntity cpsClickEntity;
   private int memberCommissionShare;
   private int userCommissionShare;
   private BigDecimal pointRate;
   private String memberName;
}

