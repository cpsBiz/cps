package com.mobcomms.shinhan.service;

import com.mobcomms.common.model.BaseResponse;
import com.mobcomms.common.model.GenericBaseResponse;
import com.mobcomms.shinhan.dto.PointBannerInfoDto;
import com.mobcomms.shinhan.dto.PointDto;
import com.mobcomms.shinhan.repository.PointBannerSettingRepository;
import com.mobcomms.shinhan.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public GenericBaseResponse<PointBannerInfoDto> getPointBannerInfo(PointBannerInfoDto model){
        //point setting 정보 조회
        var setting =  pointBannerSettingRepository.findFirstByUseYnOrderByRegDateDesc("Y");
        //유저의 Frequency 계산
        //유저의 적립 내역 조회
        var result = pointRepository.findFirstByUserKeyOrderByRegDateDesc(model.getUserKey());
        int userFrequency = 0;
        if(result != null){
            //마지막 적립 내역일시 에서 frequency를 더한 시간이 현재 시간이 보다 크면 남은 시간을 계산
            var lastPointSaveDate = result.getRegDate();
            var now = LocalDateTime.now();

            if(lastPointSaveDate.plusMinutes(setting.getFrequency()).isAfter(now)){
                userFrequency = (int) (Duration.between(lastPointSaveDate.plusMinutes(setting.getFrequency()),now)).toMinutes();
            }
        }

        var responseResult = new GenericBaseResponse<PointBannerInfoDto>();
        responseResult.setSuccess();

        //int finalUserFrequency = userFrequency;
        int finalUserFrequency = 1;
        responseResult.setData(new PointBannerInfoDto(){{
            setUserKey(model.getUserKey());
            setBadgeImageUrl(setting.getImg());
            setPointUnit(setting.getUnit());
            setBannerPoint(setting.getPoint());
            setBannerFrequency(setting.getFrequency());
            setUserFrequency(finalUserFrequency);

        }});

        //TODO AD Type Config 설정
        responseResult.getData().setAdType("C");
        responseResult.getData().setZoonId("Test");
        var adInfos = List.of(new PointBannerInfoDto.AdInfo(){{
            setAdUrl("https://link.coupang.com/re/AFFSDPWAC2?lptag=AF6181865&subid=payboocADAPIios&pageKey=1931688316&itemId=3279088954&vendorItemId=71266075808&traceid=V0-193-12930b411e2075f2&sourceType=brandstore_affiliate_omp_ads&requestid=20240624142700857153120533&vendorId=A00124679&store=true&storeId=1");
            setAdImageUrl("https://ads-partners.coupang.com/image2/xXPpLw52qHdJEbppxXMtyRnMUPB5HeBqN0Pz_4zkgrNrSyjvKIml9mTBJL_RFiEqJFrYhs-bEeBbOwlXt1-aXHf4sKvjaWwseZ74dae2nMtGixVvsFzrsK_Plch9DBaRMuliOBOepN5Z8FsZw2mVCeQFSYxHBj7Ic8U5VLr-rODZ5nF_b5xmf47wWbZykWhahModJx-PnEH81li0eGuG_T5vxHHsMMKECg0iEarDvgGIciyxTLI8XM5stlnStAuyeCp2aBqw11sD7v1IgNGMblW5TzOmVoxdgxYgXy12KzsrMzHCIiqJnN4d2oJbVwt8a386rbAentjq7CxxiMlcF-dMztc4fn8=");
        }},
        new PointBannerInfoDto.AdInfo(){{
            setAdUrl("https://ads-partners.coupang.com/image2/ZzPyDv_u7JUbzBtHZ-tEX28sWkqDRNWh384lV1rMW5MlLOv8F6maHH9j7Btg0lw2CdXIjTzAu3OpLxfycynzJ2U-0GITzvG0fkjNCIhrBhHkvgxmpro5Ydh0wNV7At12LCbCRNOde88XXcL_Cnh3R7_5QXPXT9810U6jQDsCFcORYtBLSfUsEW1TakV35oS5nnTgE5Re14BXkkM-i-S6rAMzQq9D3HPMLx-58Mf_5isBLJAbxEOzPQnfw4qz3DBYa2PYVAvzw9Ob18GE7KntJuLbX6GVMzM3dOksa0ZsXRYadxeR7ra3A-RsjhmkEHvxVZhr4aOOhieESkxPG2Y=");
            setAdImageUrl("https://link.coupang.com/re/AFFSDPWAC2?lptag=AF6181865&subid=payboocADAPIios&pageKey=5341861665&itemId=7833102480&vendorItemId=3968863972&traceid=V0-193-13ac92bf58ff9a1e&sourceType=brandstore_affiliate_omp_ads&requestid=20240624142700857153120533&vendorId=A00045273&store=true&storeId=1");
        }},
        new PointBannerInfoDto.AdInfo(){{
            setAdUrl("https://ads-partners.coupang.com/image2/ZzPyDv_u7JUbzBtHZ-tEX28sWkqDRNWh384lV1rMW5MlLOv8F6maHH9j7Btg0lw2CdXIjTzAu3OpLxfycynzJ2U-0GITzvG0fkjNCIhrBhHkvgxmpro5Ydh0wNV7At12LCbCRNOde88XXcL_Cnh3R7_5QXPXT9810U6jQDsCFcORYtBLSfUsEW1TakV35oS5nnTgE5Re14BXkkM-i-S6rAMzQq9D3HPMLx-58Mf_5isBLJAbxEOzPQnfw4qz3DBYa2PYVAvzw9Ob18GE7KntJuLbX6GVMzM3dOksa0ZsXRYadxeR7ra3A-RsjhmkEHvxVZhr4aOOhieESkxPG2Y=");
            setAdImageUrl("https://thumbnail5.coupangcdn.com/thumbnails/remote/300x100ex/image/vendor_inventory/3842/97566b98f782bac0ab0485913a48e118434a10cd16df3d580cca1f051de7.jpg");
        }});

        //TODO : 쿠팡 API 호출
        //TODO : 모비위드 API 호출
        responseResult.getData().setAdInfos(adInfos);

        return responseResult;
    }

    // point 적립 api 호출 및 적립 내역 저장
    //TODO 적립 table 인덱스 설정
    public BaseResponse callAPIPoint(PointDto model){
        var result = pointRepository.findFirstByUserKeyOrderByRegDateDesc(model.getUserKey());

        //적립 가능한 condition
        boolean isAvailableCondition = false;

        if(result == null){
            isAvailableCondition = true;
        }else {
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
        }

        //TODO : point table update

        return new BaseResponse(){{setSuccess();}};
    }
}
