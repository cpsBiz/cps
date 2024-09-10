package com.cps.finnq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cps.common.servcies.BaseHttpService;
import com.cps.finnq.dto.packet.BannerPacket;
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
public class CoupangHttpService extends BaseHttpService {
    private static String DOMAIN;

    @Autowired
    public CoupangHttpService(@Value("${coupang.domain.url}") String domain, @Value("${coupang.domain.url}") String test) {
        super(domain);
        CoupangHttpService.DOMAIN = domain;
    }

    public CoupangHttpService() {
        super(DOMAIN);
    }

    public CoupangHttpService(String domain) {
        super(domain);
    }

    public CoupangHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public CoupangHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public BannerPacket.Banner.Response SendCoupang(BannerPacket.Banner.CoupangRequest request) {
        try{
            var endPoint = request.getEndPoint();
            var result = this.GetAsync(endPoint, null, String.class,httpHeaders -> {
                httpHeaders.add("Authorization", request.getAuthorization());
            });
            BannerPacket.Banner.Response responseObj = new ObjectMapper().readValue(result.block().toString(), BannerPacket.Banner.Response.class);
            return responseObj;
        } catch (Exception ex){
            log.error("SendCoupang : {} ", ex);
            var errorResult = new BannerPacket.Banner.Response();
            return errorResult;
        }
    }
}
