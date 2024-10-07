package com.cps.api.schedule;

import com.cps.api.service.CpsRewardService;
import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsRewardPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DotPitchScheduleManager {
    @Autowired
    private final CpsRewardService cpsRewardService;

    /**
     * 도트피치 익일 호출 스케줄
     */
    @Scheduled(cron = "0 10 * * * *")
    public ResponseEntity<CpsRewardPacket.RewardInfo.DotPitchResponse> dotPitchRewardSchedule() throws Exception {
        var result = new CpsRewardPacket.RewardInfo.DotPitchResponse();
        CpsRewardPacket.RewardInfo.DotPitchRequest request = new CpsRewardPacket.RewardInfo.DotPitchRequest();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        request.setSearch_date(yesterday.format(formatter));

        try {
            var member = cpsRewardService.dotPitchReward(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(member.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(member.getResultCode(), member.getResultMessage());
            }
            result.setData(member.getData());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("dotPitchRewardSchedule Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 도트피치 매출내역 호출 스케줄
     */
    @Scheduled(cron = "0 0 9 6 * *")
    public ResponseEntity<CpsRewardPacket.RewardInfo.DotPitchResponse> dotPitchRewardMonthSchedule() throws Exception {
        var result = new CpsRewardPacket.RewardInfo.DotPitchResponse();
        CpsRewardPacket.RewardInfo.DotPitchRequest request = new CpsRewardPacket.RewardInfo.DotPitchRequest();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(2).withDayOfMonth(6);
        LocalDate endDate = today.minusMonths(1).withDayOfMonth(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                List<String> depthList = Arrays.asList("c", "o");
                for (String depth : depthList) {
                    request.setSearch_type(depth);
                    request.setSearch_date(date.format(formatter));
                    var reward = cpsRewardService.dotPitchReward(request);
                    if (Constant.RESULT_CODE_SUCCESS.equals(reward.getResultCode())) {
                        result.setSuccess();
                    } else {
                        result.setApiMessage(reward.getResultCode(), reward.getResultMessage());
                    }
                    result.setData(reward.getData());
                }
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("dotPitchRewardMonthSchedule Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}