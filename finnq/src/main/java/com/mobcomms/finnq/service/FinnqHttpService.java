package com.mobcomms.finnq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobcomms.common.servcies.BaseHttpService;
import com.mobcomms.finnq.dto.packet.FinnqPacket;
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
 * Create Date : 2024-08-23
 * Class 설명, method
 * UpdateDate : 2024-08-23, 업데이트 내용
 */
@Slf4j
@Service
public class FinnqHttpService extends BaseHttpService {
    private static String DOMAIN;
    private static String ENDPOINT;

    @Autowired
    public FinnqHttpService(@Value("${finnqDomain.domain.url}") String domain,
                            @Value("${finnqDomain.reco.url}") String endPoint) {
        super(domain, null);
        FinnqHttpService.DOMAIN = domain;
        FinnqHttpService.ENDPOINT = endPoint;
    }

    public FinnqHttpService() {
        super(DOMAIN);
    }

    public FinnqHttpService(String domain) {
        super(domain);
    }

    public FinnqHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public FinnqHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public FinnqPacket.GetFinnqInfo.Response SendFinnq(FinnqPacket.GetFinnqInfo.Request request) {
        try{
            var result = this.PostAsync(ENDPOINT,request,FinnqPacket.GetFinnqInfo.Response.class);
            return result.block();
        } catch (Exception ex) {
            log.error("SendFinnq : {} ", ex);
            var errorResult = new FinnqPacket.GetFinnqInfo.Response();
            errorResult.setRsltCd(new FinnqPacket.GetFinnqInfo.Response().getRsltCd());
            return errorResult;
        }
    }
}
