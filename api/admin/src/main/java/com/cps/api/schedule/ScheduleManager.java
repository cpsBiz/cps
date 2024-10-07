package com.cps.api.schedule;

import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsViewSchedulePacket;
import com.cps.cpsService.service.CpsScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleManager {

    @Autowired
    private CpsScheduleService cpsScheduleService;

    /**
     * 통계 스케줄
     */
    @Scheduled(cron = "0 30  * * * *")
    public ResponseEntity<CpsViewSchedulePacket.ScheduleInfo.ViewSchduelResponse> summarySchedule() throws Exception {
        CpsViewSchedulePacket.ScheduleInfo.ViewScheduleRequest request = new CpsViewSchedulePacket.ScheduleInfo.ViewScheduleRequest();
        request.setMinute(60);
        var result = new CpsViewSchedulePacket.ScheduleInfo.ViewSchduelResponse();

        try {
            var view = cpsScheduleService.summarySchedule(request);
            if (Constant.RESULT_CODE_SUCCESS.equals(view.getResultCode())) {
                result.setSuccess();
            } else {
                result.setApiMessage(view.getResultCode(), view.getResultMessage());
            }
            result.setDatas(view.getDatas());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("summarySchedule Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 파티션 스케줄
     */
    @Scheduled(cron = "0 0 3 1 * *")
    public void partitionSchedule() throws Exception {

    }
}