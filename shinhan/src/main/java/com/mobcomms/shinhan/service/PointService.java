package com.mobcomms.shinhan.service;

import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.common.utils.StringUtils;
import com.mobcomms.shinhan.constant.ShinhanConstant;
import com.mobcomms.shinhan.dto.PointBannerInfoDto;
import com.mobcomms.shinhan.dto.PointDto;
import com.mobcomms.shinhan.dto.packet.CoupangPacket;
import com.mobcomms.shinhan.entity.AdClickEntity;
import com.mobcomms.shinhan.entity.PointEntity;
import com.mobcomms.shinhan.repository.AdClickRepository;
import com.mobcomms.shinhan.repository.PointBannerSettingRepository;
import com.mobcomms.shinhan.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by enliple
 * Create Date : 2024-06-25
 * Class 설명, method
 * UpdateDate : 2024-06-25, 업데이트 내용
 */
@RequiredArgsConstructor
@Service
public class PointService {

    private final PointRepository pointRepository;
    private final PointBannerSettingRepository pointBannerSettingRepository;
    private final AdClickRepository adClickRepository;

    private final MobwithHttpService mobwithHttpService;
    private final CoupangHttpService coupangHttpService;

    @Value("${zone.gamezone.aos.300_250}")
    private String zoneGameAos;
    @Value("${zone.gamezone.ios.300_250}")
    private String zoneGameIos;

    @Value("${zone.point.aos.320_100}")
    private String zonePointAos;
    @Value("${zone.point.ios.320_100}")
    private String zonePointIos;

    private String coupangGameAos = "shinhansadariaos";
    private String coupangGameIos = "shinhansadariios";

    private String coupangPointAos = "shinhanpointaos";
    private String coupangPointIos = "shinhanpointios";


    public GenericBaseResponse<PointBannerInfoDto> getPointBannerInfo(PointBannerInfoDto model){
        var responseResult = new GenericBaseResponse<PointBannerInfoDto>();

        //point setting 정보 조회
        var pointBannerSettingResult =  pointBannerSettingRepository.findFirstByUseYnOrderByRegDateDesc("Y");

        //유저의 마지막 적립 내역 조회
        var userPointResult = pointRepository.findFirstByUserKeyOrderByRegDateDesc(model.getUserKey());

        //유저의 오늘 적립 내역 list 조회
        int today = Integer.parseInt(StringUtils.getDateyyyyMMdd()) ;
        var userPointTodayResult = pointRepository.findByUserKeyAndStatsDttm(model.getUserKey(),today);
        int userTodaySavePointCount =  userPointTodayResult.size();

        String userSaveFlag = "Y";
        if(userTodaySavePointCount >= pointBannerSettingResult.getMaxPoint()){
            userSaveFlag = "N";
        }
        //적립 가능 여부 flag
        String finalUserSaveFlag = userSaveFlag;

        //유저의 Frequency
        int userFrequency = 0;
        if(userPointResult != null){
            //마지막 적립 내역일시 에서 frequency를 더한 시간이 현재 시간이 보다 크면 남은 시간을 계산
            var lastPointSaveDate = userPointResult.getRegDate();
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
        var getMobwithAdInfoResult =  mobwithHttpService.GetMobwithAdInfo(mobiwithZoneId,model.getAdid());

        if(getMobwithAdInfoResult.getCode().equals("0000")){
            responseResult.getData().setAdType("M");
            responseResult.getData().setZoonId(mobiwithZoneId);
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
            responseResult.getData().setZoonId(coupangZoneId);
            var request = new CoupangPacket.GetCoupangAdInfo.Request();
            request.setDeviceId(model.getAdid());
            request.setSubId(coupangZoneId);
            request.setImageSize("100x100");
            var getCoupangAdInfoResult =   coupangHttpService.GetCoupangAdInfo(request);
            getCoupangAdInfoResult.getData().forEach(item->{
                var data = new PointBannerInfoDto.AdInfo(){{
                    setAdImageUrl(item.getProductImage());
                    setAdUrl(item.getProductUrl());
                }};
                adInfos.add(data);
            });
        }

        responseResult.getData().setAdInfos(adInfos);
        return responseResult;
    }

    public GenericBaseResponse<PointBannerInfoDto> getGameZoneAdInfo(PointBannerInfoDto model){
        var responseResult = new GenericBaseResponse<PointBannerInfoDto>();
        var resultData = new PointBannerInfoDto();
        final List<PointBannerInfoDto.AdInfo> adInfos = new ArrayList<>();
        var mobiwithZoneId = model.getOs().equals("A")?zoneGameAos:zoneGameIos;
        resultData.setZoonId(mobiwithZoneId);
        var getMobwithAdInfoResult =  mobwithHttpService.GetMobwithAdInfo(mobiwithZoneId,model.getAdid());

        if(getMobwithAdInfoResult.getCode().equals("0000")){
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
            resultData.setZoonId(coupangZoneId);
            var request = new CoupangPacket.GetCoupangAdInfo.Request();
            request.setDeviceId(model.getAdid());
            request.setSubId(coupangZoneId);
            request.setImageSize("300x250");
            var getCoupangAdInfoResult =   coupangHttpService.GetCoupangAdInfo(request);
            getCoupangAdInfoResult.getData().forEach(item->{
                var data = new PointBannerInfoDto.AdInfo(){{
                    setAdImageUrl(item.getProductImage());
                    setAdUrl(item.getProductUrl());
                }};
                adInfos.add(data);
            });
        }

        resultData.setAdInfos(adInfos);
        responseResult.setSuccess();
        responseResult.setData(resultData);
        return responseResult;
    }

    // point 적립 api 호출 및 적립 내역 저장
    //TODO 적립 table 인덱스 설정
    public BaseResponse callAPIPoint(PointDto model){
        int today = Integer.parseInt(StringUtils.getDateyyyyMMdd()) ;
        //클릭 정보 저장.
        var adClickEntity =  new AdClickEntity();
        adClickEntity.setUserKey(model.getUserKey());
        adClickEntity.setAdUrl(model.getAdUrl());
        adClickEntity.setType(ShinhanConstant.AD_TYPE_POINT_BANNER);
        adClickEntity.setZoneId(model.getZoneId());
        adClickEntity.setStatsDttm(today);
        adClickEntity.setRegDate(LocalDateTime.now());
        adClickRepository.save(adClickEntity);

        var result = pointRepository.findFirstByUserKeyOrderByRegDateDesc(model.getUserKey());
        //point setting 정보 조회
        var pointBannerSettingResult =  pointBannerSettingRepository.findFirstByUseYnOrderByRegDateDesc("Y");
        int savePoint = pointBannerSettingResult.getPoint();
        //적립 가능한 condition
        boolean isAvailableCondition = false;

        if(result == null){
            isAvailableCondition = true;
        } else {
            var setting =  pointBannerSettingRepository.findFirstByUseYnOrderByRegDateDesc("Y");
            int frequency  = setting.getFrequency();
            //마지막 적립 내역일시 에서 frequency를 더한 시간이 현재 시간이 보다 작으면 적립 가능
            // 12:30 + 60 분 = 13:30,  13:30 < now
            if(result.getRegDate().plusMinutes(frequency).isBefore(LocalDateTime.now())){
                isAvailableCondition = true;
            }
        }
        //적립이 가능하면,
        if(isAvailableCondition){
            //TODO 적립 API 호출
            PointEntity pointEntity = new PointEntity();
            pointEntity.setUserKey(model.getUserKey());
            pointEntity.setStatus("Success");
            pointEntity.setOs(model.getOs());
            pointEntity.setRegDate(LocalDateTime.now());
            pointEntity.setStatsDttm(today);
            pointEntity.setZoneId(model.getZoneId());
            pointEntity.setPoint(savePoint);
            pointEntity.setAdUrl(model.getAdUrl());
            pointRepository.save(pointEntity);
            return new BaseResponse(){{setSuccess();}};
        }
        else {
            return new BaseResponse(){{setError("BannerFrequency Time");}};
        }
    }
}
