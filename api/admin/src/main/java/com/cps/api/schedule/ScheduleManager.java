package com.cps.api.schedule;

import com.cps.api.service.GiftiShowService;
import com.cps.common.constant.Constant;
import com.cps.cpsService.packet.CpsRewardPacket;
import com.cps.cpsService.packet.CpsViewSchedulePacket;
import com.cps.cpsService.service.CpsGiftService;
import com.cps.cpsService.service.CpsScheduleService;
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
public class ScheduleManager {

    @Autowired
    private CpsScheduleService cpsScheduleService;

    @Autowired
    private GiftiShowService giftiShowService;

    /**
     * 통계 스케줄
     */
    @Scheduled(cron = "0 10 * * * *")
    public ResponseEntity<CpsViewSchedulePacket.ScheduleInfo.ViewSchduelResponse> summarySchedule() throws Exception {
        CpsViewSchedulePacket.ScheduleInfo.ViewScheduleRequest request = new CpsViewSchedulePacket.ScheduleInfo.ViewScheduleRequest();
        request.setDayMinute(60); request.setHourMinute(60);
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
     * 통계 월 스케줄
     */
    @Scheduled(cron = "0 0 7 6 * *")
    public ResponseEntity<CpsViewSchedulePacket.ScheduleInfo.ViewSchduelResponse> summaryScheduleMonth() throws Exception {
        CpsViewSchedulePacket.ScheduleInfo.ViewScheduleMonthRequest request = new CpsViewSchedulePacket.ScheduleInfo.ViewScheduleMonthRequest();

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(2).withDayOfMonth(6);
        LocalDate endDate = today.minusMonths(1).withDayOfMonth(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        var result = new CpsViewSchedulePacket.ScheduleInfo.ViewSchduelResponse();

        try {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                request.setSearchDay(Integer.parseInt(date.format(formatter)));
                var view = cpsScheduleService.summaryScheduleMonth(request);
                if (Constant.RESULT_CODE_SUCCESS.equals(view.getResultCode())) {
                    result.setSuccess();
                } else {
                    result.setApiMessage(view.getResultCode(), view.getResultMessage());
                }
                result.setDatas(view.getDatas());
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.setError("summaryScheduleMonth Error");
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 상품 등록 스케줄
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void giftProduct() throws Exception {
        giftiShowService.giftiShowBizProduct();
    }

    /**
     * 기프티콘 만료
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void giftiConEnd() throws Exception {
        LocalDate yesterday = LocalDate.now().minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            giftiShowService.giftiConEnd(Integer.parseInt(yesterday.format(formatter)));
        } catch (Exception e) {
            log.error("giftiConEnd Controller Error");
        }
    }

    /**
     * 파티션 스케줄
     */
    @Scheduled(cron = "0 0 3 1 * *")
    public void partitionSchedule() throws Exception {

    }
}