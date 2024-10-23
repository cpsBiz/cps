package com.cps.common.dto;

import com.cps.common.entity.CpsClickEntity;
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

