package com.mobcomms.shinhan.service;

import com.mobcomms.common.servcies.BaseHttpService;
import com.mobcomms.shinhan.dto.packet.ShinhanPacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.util.List;
import java.util.function.Consumer;

/*
 * Created by enliple
 * Create Date : 2024-07-04
 * Class 설명, method
 * UpdateDate : 2024-07-04, 업데이트 내용
 */
@Slf4j
@Service
public class ShinhanHttpService extends BaseHttpService {
    private static String DOMAIN;
    private static String ENDPOINT;
    private static String APIKEY;

    @Autowired
    public ShinhanHttpService(@Value("${shinhan.domain.url}") String domain,
                              @Value("${shinhan.reco.url}") String endPoint,
                              @Value("${shinhan.api.key}") String apiKey) {
        super(domain);
        ShinhanHttpService.DOMAIN = domain;
        ShinhanHttpService.ENDPOINT = endPoint;
        ShinhanHttpService.APIKEY = apiKey;
    }

    public ShinhanHttpService() {
        super(DOMAIN);
    }

    public ShinhanHttpService(String domain) {
        super(domain);
    }

    public ShinhanHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public ShinhanHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public ShinhanPacket.SendShinhan.Response SendShinhan(ShinhanPacket.SendShinhan.Request request) {
        try{
            var result = this.PostAsync(ENDPOINT,request,ShinhanPacket.SendShinhan.Response.class,httpHeaders -> {
                httpHeaders.add("apiKey", APIKEY);
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            });
            return result.block();
        } catch (Exception ex){
            var errorResult = new ShinhanPacket.SendShinhan.Response();
            errorResult.setDataBody(new ShinhanPacket.ResponseBody());
            errorResult.getDataBody().setRcd("9999");
            log.error("SendShinhan : {} ", ex);
            return errorResult;
        }
    }
}
