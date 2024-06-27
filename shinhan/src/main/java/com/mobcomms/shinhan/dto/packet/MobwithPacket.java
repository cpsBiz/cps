package com.mobcomms.shinhan.dto.packet;

import org.springframework.beans.factory.annotation.Value;

/*
 * Created by enliple
 * Create Date : 2024-06-27
 * Class 설명, method
 * UpdateDate : 2024-06-27, 업데이트 내용
 */
public class MobwithPacket {

    @Value("${mobwith.domain.url}")
    private String Domain;
    @Value("${mobwith.param.url}")
    private String Endpoint;
}
