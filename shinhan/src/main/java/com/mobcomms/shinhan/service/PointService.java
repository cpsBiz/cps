package com.mobcomms.shinhan.service;

import com.google.gson.Gson;
import com.mobcomms.common.api.ResultCode;
import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.common.utils.StringUtils;
import com.mobcomms.shinhan.dto.PointBannerInfoDto;
import com.mobcomms.shinhan.dto.PointDto;
import com.mobcomms.shinhan.dto.packet.CoupangPacket;
import com.mobcomms.shinhan.dto.packet.ShinhanPacket;
import com.mobcomms.shinhan.entity.AdClickEntity;
import com.mobcomms.shinhan.entity.PointEntity;
import com.mobcomms.shinhan.repository.AdClickRepository;
import com.mobcomms.shinhan.repository.PointBannerSettingRepository;
import com.mobcomms.shinhan.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PointService {
    private final PointRepository pointRepository;
    private final PointBannerSettingRepository pointBannerSettingRepository;
    private final AdClickRepository adClickRepository;

    private final MobwithHttpService mobwithHttpService;
    private final CoupangHttpService coupangHttpService;
    private final ShinhanHttpService shinhanHttpService;

    //TODO 운영 배포시 지면 코드 변경
    @Value("${zone.gamezone.aos.300_250}")
    private String zoneGameAos;
    @Value("${zone.gamezone.ios.300_250}")
    private String zoneGameIos;

    @Value("${zone.point.aos.320_100}")
    private String zonePointAos;
    @Value("${zone.point.ios.320_100}")
    private String zonePointIos;

    @Value("${coupang.game.aos}")
    private String coupangGameAos;
    @Value("${coupang.game.ios}")
    private String coupangGameIos;

    @Value("${coupang.point.aos}")
    private String coupangPointAos;
    @Value("${coupang.point.ios}")
    private String coupangPointIos;
    @Value("${coupang.noimage}")
    private String noImage;


    private final String API_RESULT_SUCESS_CODE = "0000";

    // 포인트 배너 정보 조회
    public GenericBaseResponse<PointBannerInfoDto> getPointBannerInfo(PointBannerInfoDto model){
        var responseResult = new GenericBaseResponse<PointBannerInfoDto>();
        try{
            if (model.getAdid().equals("ios_option_disabled") || model.getAdid().equals("aos_option_disabled")) {
                model.setAdid("noadid00-0000-0000-0000-000000000000");
            }
            int today = Integer.parseInt(StringUtils.getDateyyyyMMdd()) ;

            //point setting 정보 조회
            var pointBannerSettingResult =  pointBannerSettingRepository.findFirstByUseYnOrderByRegDateDesc("Y");

            //유저의 오늘 적립 내역 list 조회
            List<PointEntity> userPointTodayResult = pointRepository.findFirstByUserKeyAndCodeAndStatsDttmOrderByRegDateDesc(model.getUserKey(), API_RESULT_SUCESS_CODE,today);
            int userTodaySavePointCount =  userPointTodayResult.size();

            String userSaveFlag = "Y";
            if(userTodaySavePointCount >= pointBannerSettingResult.getMaxPoint()){
                userSaveFlag = "N";
            }
            //적립 가능 여부 flag
            String finalUserSaveFlag = userSaveFlag;

            //유저의 Frequency
            int userFrequency = 0;
            if (userPointTodayResult.size() > 0) {
                //유저의 마지막 적립 내역 조회
                Optional<PointEntity> userPointResult = userPointTodayResult.stream().findFirst();

                //마지막 적립 내역일시 에서 frequency를 더한 시간이 현재 시간이 보다 크면 남은 시간을 계산
                var lastPointSaveDate = userPointResult.get().getRegDate();
                var now = LocalDateTime.now();

                if(lastPointSaveDate.plusMinutes(pointBannerSettingResult.getFrequency()).isAfter(now)){
                    userFrequency = (int) (Duration.between(now,lastPointSaveDate.plusMinutes(pointBannerSettingResult.getFrequency()))).toMinutes() + 1;
                }
            }

            responseResult.setSuccess();
            int finalUserFrequency = userFrequency;

            //TODO mapper 적용
            responseResult.setData(new PointBannerInfoDto(){{
                setUserKey(model.getUserKey());
                setBadgeImageUrl(pointBannerSettingResult.getImg());
                setPointUnit(pointBannerSettingResult.getUnit());
                setBannerPoint(pointBannerSettingResult.getPoint());
                setBannerFrequency(pointBannerSettingResult.getFrequency());
                setUserFrequency(finalUserFrequency);
                setUserPointFlag(finalUserSaveFlag);
            }});

            final List<PointBannerInfoDto.AdInfo> adInfos = new ArrayList<>();

            //TODO : 모비위드 API 호출
            var mobiwithZoneId = model.getOs().equals("A")?zonePointAos:zonePointIos;
            var getMobwithAdInfoResult =  mobwithHttpService.getMobwithAdInfo(mobiwithZoneId,model.getAdid());

            if (getMobwithAdInfoResult.getCode().equals(API_RESULT_SUCESS_CODE)) {
                responseResult.getData().setAdType("M");
                responseResult.getData().setZoneId(mobiwithZoneId);
                getMobwithAdInfoResult.getData().getData().forEach(item->{
                    var data = new PointBannerInfoDto.AdInfo(){{
                        setAdImageUrl(item.getPImg());
                        setAdUrl(item.getPUrl());
                    }};
                    adInfos.add(data);
                });
            } else {
                //TODO : 쿠팡 API 호출
                responseResult.getData().setAdType("C");
                var coupangZoneId = model.getOs().equals("A")?coupangPointAos:coupangPointIos;
                responseResult.getData().setZoneId(coupangZoneId);
                var request = new CoupangPacket.GetCoupangAdInfo.Request();
                request.setDeviceId(model.getAdid());
                request.setSubId(coupangZoneId);
                request.setImageSize("100x100");
                var getCoupangAdInfoResult =   coupangHttpService.getCoupangAdInfo(request);

                if(null != getCoupangAdInfoResult.getData()) {
                    getCoupangAdInfoResult.getData().forEach(item -> {
                        var data = new PointBannerInfoDto.AdInfo() {{
                            setAdImageUrl(item.getProductImage());
                            setAdUrl(item.getProductUrl());
                        }};
                        adInfos.add(data);
                    });
                } else {
                    //TODO : 쿠팡 데이터 없는 경우
                    log.error("[getPointBannerInfo] coupang Null request : " + request);
                    log.error("[getPointBannerInfo] coupang Null getCoupangAdInfoResult : " + getCoupangAdInfoResult);
                    var data = new PointBannerInfoDto.AdInfo() {{
                        setAdImageUrl(noImage);
                        setAdUrl("https://www.mobon.net/bridge");
                    }};
                    //신한카드 배너 광고는 2개씩 표시
                    adInfos.add(data);
                    adInfos.add(data);
                }
            }
            responseResult.getData().setAdInfos(adInfos);
        } catch (Exception e) {
            log.error("getPointBannerInfo Exception : {} ", e);
            throw  e;
        }

        return responseResult;
    }

    // 사다리타기 배너 정보 조회
    public GenericBaseResponse<PointBannerInfoDto> getGameZoneAdInfo(PointBannerInfoDto model){
        var responseResult = new GenericBaseResponse<PointBannerInfoDto>();
        try{
            if (model.getAdid().equals("ios_option_disabled") || model.getAdid().equals("aos_option_disabled")) {
                model.setAdid("noadid00-0000-0000-0000-000000000000");
            }
            var resultData = new PointBannerInfoDto();
            final List<PointBannerInfoDto.AdInfo> adInfos = new ArrayList<>();
            var mobiwithZoneId = model.getOs().equals("A")?zoneGameAos:zoneGameIos;
            resultData.setZoneId(mobiwithZoneId);
            var getMobwithAdInfoResult =  mobwithHttpService.getMobwithAdInfo(mobiwithZoneId,model.getAdid());

            if (getMobwithAdInfoResult.getCode().equals(API_RESULT_SUCESS_CODE)) {
                resultData.setAdType("M");
                getMobwithAdInfoResult.getData().getData().forEach(item->{
                    var data = new PointBannerInfoDto.AdInfo(){{
                        setAdImageUrl(item.getPImg());
                        setAdUrl(item.getPUrl());
                    }};
                    adInfos.add(data);
                });
            } else {
                //TODO : 쿠팡 API 호출
                resultData.setAdType("C");
                var coupangZoneId = model.getOs().equals("A")?coupangGameAos:coupangGameIos;
                resultData.setZoneId(coupangZoneId);
                var request = new CoupangPacket.GetCoupangAdInfo.Request();
                request.setDeviceId(model.getAdid());
                request.setSubId(coupangZoneId);
                request.setImageSize("300x250");
                var getCoupangAdInfoResult =   coupangHttpService.getCoupangAdInfo(request);

                if(null != getCoupangAdInfoResult.getData()) {
                    getCoupangAdInfoResult.getData().forEach(item -> {
                        var data = new PointBannerInfoDto.AdInfo() {{
                            setAdImageUrl(item.getProductImage());
                            setAdUrl(item.getProductUrl());
                        }};
                        adInfos.add(data);
                    });
                } else {
                    //TODO : 쿠팡 데이터 없는 경우
                    log.error("[getGameZoneAdInfo] coupang Null request : " + request);
                    log.error("[getGameZoneAdInfo] coupang Null getCoupangAdInfoResult : " + getCoupangAdInfoResult);
                    var data = new PointBannerInfoDto.AdInfo() {{
                        setAdImageUrl(noImage);
                        setAdUrl("https://www.mobon.net/bridge");
                    }};
                    adInfos.add(data);
                }
            }

            resultData.setAdInfos(adInfos);
            responseResult.setSuccess();
            responseResult.setData(resultData);
        }catch (Exception e) {
            log.error("getGameZoneAdInfo Exception : {} ", e);
            throw e;
        }

        return responseResult;
    }

    // point 적립 api 호출 및 적립 내역 저장
    public BaseResponse callAPIPoint(PointDto model) {
        var result = new BaseResponse();
        AdClickEntity adClickEntity = new AdClickEntity();
        PointEntity pointEntity = new PointEntity();
        UUID u = UUID.randomUUID();

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            int today = Integer.parseInt(StringUtils.getDateyyyyMMdd());

            //클릭 정보 저장.
            adClickEntity.setStatsDttm(today);
            adClickEntity.setUserKey(model.getUserKey());
            adClickEntity.setZoneId(model.getZoneId());
            adClickEntity.setAdUrl(model.getAdUrl());
            adClickEntity.setIpAddress(ipAddress);
            adClickRepository.save(adClickEntity);

            //point setting 정보 조회
            var pointBannerSettingResult = pointBannerSettingRepository.findFirstByUseYnOrderByRegDateDesc("Y");
            int savePoint = pointBannerSettingResult.getPoint();

            //오늘 적립 내역의 max point 에 대한 적립 vaild 처리
            List<PointEntity> userPointTodayResult = pointRepository.findFirstByUserKeyAndCodeAndStatsDttmOrderByRegDateDesc(model.getUserKey(), API_RESULT_SUCESS_CODE, today);
            int userTodaySavePointCount = userPointTodayResult.size();
            if (userTodaySavePointCount >= pointBannerSettingResult.getMaxPoint()) {
                return new BaseResponse() {{
                    setError("Saved point to today's limit");
                }};
            }

            //Frequency 에 대한 적립 vaild 처리
            boolean isAvailableCondition = false;
            if (userTodaySavePointCount == 0) {
                isAvailableCondition = true;
            } else {
                Optional<PointEntity> todaySavvePointresult = userPointTodayResult.stream().findFirst();
                var setting = pointBannerSettingRepository.findFirstByUseYnOrderByRegDateDesc("Y");
                int frequency = setting.getFrequency();
                //마지막 적립 내역일시 에서 frequency를 더한 시간이 현재 시간이 보다 작으면 적립 가능
                // 12:30 + 60 분 = 13:30,  13:30 < now
                if (todaySavvePointresult.get().getRegDate().plusMinutes(frequency).isBefore(LocalDateTime.now())) {
                    isAvailableCondition = true;
                }
            }

            if (isAvailableCondition) {
                //실제 적립 API 통신전에 동시 호출로 인한 중복요청을 방지하기 위해  pointEntity 저장을 한다.
                pointEntity.setUserKey(model.getUserKey());
                pointEntity.setStatsDttm(today);
                pointEntity.setCode("0");
                pointEntity.setShinhanCode("Fail");
                pointEntity.setPoint(savePoint);
                pointEntity.setZoneId(model.getZoneId());
                pointEntity.setOs(model.getOs());
                pointEntity.setAdUrl(model.getAdUrl());
                pointEntity.setTransactionId("0");
                pointEntity.setIpAddress(ipAddress);

                if (null != pointRepository.save(pointEntity)) {
                    ShinhanPacket.SendShinhan.Response response = shinhanHttpService.SendShinhan(shinhanPacket(pointEntity)); //신한 api 통신
                    //신한 카드 적립 성공 체크
                    String rcd = null;
                    if(response != null){
                        var dataBody = response.getDataBody();
                        if(dataBody != null){
                            if(dataBody.getRcd() != null){
                                rcd =  dataBody.getRcd();
                            }
                        }
                    }

                    if (rcd != null && rcd.equals(API_RESULT_SUCESS_CODE)) {
                        //적립 API 성공
                        pointEntity.setCode(ResultCode.SUCCESS.getResultCode());
                        pointEntity.setShinhanCode(ResultCode.SUCCESS.getResultCode());
                        result.setSuccess();
                    } else {
                        //적립 API 실패 건
                        //Case DataBody 가 없는 경우 (해더 API키 잘못)
                        //Case DataBody 실패 사유 rcd
                        pointEntity.setCode(ResultCode.ERROR.getResultCode());
                        //실패 사유를 저장
                        var apiStatus =  (response.getDataBody() != null) && (response.getDataBody().getRcd() != null) ? response.getDataBody().getRcd() : "error";
                        pointEntity.setShinhanCode(apiStatus);
                        result.setError(apiStatus);
                        if(apiStatus.equals("error")){
                            log.error("shinhan api error response : {} " + response);
                        }
                    }
                    pointEntity.setTransactionId(u.toString());
                    pointRepository.save(pointEntity);
                } else {
                    result.setError("point entity save Fail");
                }
            } else {
                result.setError("BannerFrequency Time");
            }
            return result;
        } catch (Exception e) {
            log.error("callAPIPoint : {} ", e);
            return new BaseResponse() {{
                setError("callAPIPoint Exception");
            }};
        }
    }

    public ShinhanPacket.SendShinhan.Request shinhanPacket(PointEntity pointEntity){
        var header = new ShinhanPacket.Header();
        header.setReqKey(Long.toString(pointEntity.getPointSeq()));

        var body = new ShinhanPacket.Body();
        body.setEcrClnn(pointEntity.getUserKey());
        body.setAfoCVl("APPTECH04_01");
        body.setPnt(Integer.toString(pointEntity.getPoint()));

        var packet = new ShinhanPacket.SendShinhan.Request();
        packet.setDataHeader(header);
        packet.setDataBody(body);
        return packet;
    }

    /**
     * 통신 오류 체크
     * @date 2024-06-11
     */
    public void updateZero() throws Exception {
        pointRepository.updateCodeAndModDateAndUniqueInsertByStatusDttm(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "9999","3007");
    }
}
