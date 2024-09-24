package com.cps.cpsApi.dto;

import lombok.Data;

@Data
public class DotPitchRewardDto {
    String orderid;
    String m_name;
    String aff_id;
    String p_name;
    int quantity;
    int price ;
    int comm;
    int commission_rate;
    String a_info;
    String order_flag;
}

