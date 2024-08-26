package com.mobcomms.finnq.controller;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */

import com.mobcomms.finnq.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleController {
    private final PointService pointService;

    /**
     * point, offerwall 파티션 관리
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void updatePartition() throws Exception {
        String local = InetAddress.getLocalHost().getHostAddress();
        if (null != local) {
            if("211.62.59.210".equals(local))pointService.updateZero();
        }
    }
}
