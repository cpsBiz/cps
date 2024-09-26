package com.cps.cpsApi.schedule;

import com.cps.common.constant.Constant;
import com.cps.cpsApi.packet.CpsRewardPacket;
import com.cps.cpsApi.service.CpsRewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class DotPitchScheduleManager {
    @Autowired
    private final CpsRewardService cpsRewardService;

    /**
     * 도트피치 익일 호출 스케줄
     */
    @Scheduled(cron = "0 0 9 * * *")
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
    @Scheduled(cron = "0 0 13 6 * *")
    public ResponseEntity<CpsRewardPacket.RewardInfo.DotPitchResponse> dotPitchRewardMonthSchedule() throws Exception {
        var result = new CpsRewardPacket.RewardInfo.DotPitchResponse();
        CpsRewardPacket.RewardInfo.DotPitchRequest request = new CpsRewardPacket.RewardInfo.DotPitchRequest();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(2).withDayOfMonth(6);
        LocalDate endDate = today.minusMonths(1).withDayOfMonth(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                //취소
                request.setSearch_type("c");
                request.setSearch_date(date.format(formatter));
                var cancel = cpsRewardService.dotPitchReward(request);
                if (Constant.RESULT_CODE_SUCCESS.equals(cancel.getResultCode())) {
                    result.setSuccess();
                } else {
                    result.setApiMessage(cancel.getResultCode(), cancel.getResultMessage());
                }

                //확정
                request.setSearch_type("o");
                request.setSearch_date(date.format(formatter));
                var reward = cpsRewardService.dotPitchReward(request);
                if (Constant.RESULT_CODE_SUCCESS.equals(reward.getResultCode())) {
                    result.setSuccess();
                } else {
                    result.setApiMessage(reward.getResultCode(), reward.getResultMessage());
                }
                result.setData(reward.getData());
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("dotPitchRewardMonthSchedule Controller Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}