package com.mobcomms.shinhan.service;

import com.mobcomms.common.servcies.BaseHttpService;
import com.mobcomms.shinhan.dto.packet.MobwithPacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.util.List;
import java.util.function.Consumer;

/*
 * Created by enliple
 * Create Date : 2024-06-27
 * Class 설명, method
 * UpdateDate : 2024-06-27, 업데이트 내용
 */

@Slf4j
@Service
public class MobwithHttpService  extends BaseHttpService {
    //TODO 운영 배포시 도메인 변경
    /*public static final String DOMAIN = "https://www.mobwithad.com";
    public static final String GET_MOBWITH_AD_INFO_ENDPOINT = "/api/banner/app/mobicomms/v1/shcard?output=json&zone=";*/

    private static String DOMAIN;
    private static String GET_MOBWITH_AD_INFO_ENDPOINT;

    @Autowired
    public MobwithHttpService(@Value("${mobwithad.domain.url}") String domain,
                              @Value("${mobwithad.reco.url}") String getMobwithAdInfoEndpoint) {
        super(domain);
        MobwithHttpService.DOMAIN = domain;
        MobwithHttpService.GET_MOBWITH_AD_INFO_ENDPOINT = getMobwithAdInfoEndpoint;
    }

    public MobwithHttpService(String domain) {
        super(domain);
    }

    public MobwithHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public MobwithHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public MobwithPacket.GetMobwithAdInfo.Response getMobwithAdInfo(String zoneId, String adid){
        try{

            var endPoint = GET_MOBWITH_AD_INFO_ENDPOINT + zoneId + "&adid=" + adid;
            log.info("[GetMobwithAdInfo] endPoint : " + endPoint);

            var result = this.GetAsync(endPoint,null,MobwithPacket.GetMobwithAdInfo.Response.class);
            return result.block();
        } catch (Exception ex){
            var error = new MobwithPacket.GetMobwithAdInfo.Response();
            error.setCode("-9999");
            error.setMessage(ex.getMessage());
            return error;
        }
    }

}