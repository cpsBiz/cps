package com.mobcomms.hanapay.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobcomms.common.servcies.BaseHttpService;
import com.mobcomms.hanapay.dto.packet.HanaPacket;
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
 * Create Date : 2024-07-04
 * Class 설명, method
 * UpdateDate : 2024-08-14, 업데이트 내용
 */
@Slf4j
@Service
public class HanaHttpService extends BaseHttpService {
    private static String DOMAIN;
    private static String ENDPOINT;

    @Autowired
    public HanaHttpService(@Value("${hanaPayDomain.domain}") String domain,
                           @Value("${hanaPayDomain.uri}") String endPoint) {
        super(domain, null);
        HanaHttpService.DOMAIN = domain;
        HanaHttpService.ENDPOINT = endPoint;
    }

    public HanaHttpService() {
        super(DOMAIN);
    }

    public HanaHttpService(String domain) {
        super(domain);
    }

    public HanaHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public HanaHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public HanaPacket.GetHanaInfo.Response SendHana(HanaPacket.GetHanaInfo.Request request) {
        try{
            var endPoint = ENDPOINT;
            var result = this.GetAsync(endPoint, request, String.class);
            HanaPacket.GetHanaInfo.Response responseObj = new ObjectMapper().readValue(result.block().toString(), HanaPacket.GetHanaInfo.Response.class);
            return responseObj;
        } catch (Exception ex){
            log.error("SendHana : {} ", ex);
            var errorResult = new HanaPacket.GetHanaInfo.Response();
            errorResult.setData(new HanaPacket.ResultList());
            errorResult.getData().getResultList().get(0).setResultCode("9999");
            return errorResult;
        }
    }
}
