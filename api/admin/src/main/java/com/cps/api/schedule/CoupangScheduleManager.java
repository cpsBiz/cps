package com.cps.api.schedule;

import com.cps.api.service.CpsRewardCoupangService;
import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsRewardPacket;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoupangScheduleManager {

    @Autowired
    private final CpsRewardCoupangService cpsRewardCoupangService;

    /**
     * 쿠팡 익일 호출 스케줄
     */
    @Scheduled(cron = "0 0 18 * * *")
    public ResponseEntity<CpsRewardPacket.RewardInfo.RewardResponse> coupangSchedule() throws Exception {
        var result = new CpsRewardPacket.RewardInfo.RewardResponse();
        CpsRewardPacket.RewardInfo.CoupangRequest request = new CpsRewardPacket.RewardInfo.CoupangRequest();

        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        request.setStartDate(yesterday.format(formatter));
        request.setEndDate(yesterday.format(formatter));

        try {
            List<String> depthList = Arrays.asList("N", "Y");
            for (String rewardYn : depthList) {
                var reward = cpsRewardCoupangService.coupangReward(request, rewardYn);
                if (Constant.RESULT_CODE_SUCCESS.equals(reward.getResultCode())) {
                    result.setSuccess();
                } else {
                    result.setApiMessage(reward.getResultCode(), reward.getResultMessage());
                }
                result.setData(reward.getData());
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("coupangSchedule Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 쿠팡 사탕 지급 확정
     */
    @Scheduled(cron = "30 6 11 * * *")
    public void coupangStickSchedule() throws Exception {
        CpsRewardPacket.RewardInfo.CoupangStickRequest request = new CpsRewardPacket.RewardInfo.CoupangStickRequest();
        LocalDate yesterday = LocalDate.now().minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        request.setRegDay(Integer.parseInt(yesterday.format(formatter)));

        try {
            cpsRewardCoupangService.coupangStickSchedule(request);
        } catch (Exception e) {
            log.error("coupangStickSchedule Controller Error");
        }
    }
}