package com.cps.cpsApi.service;

import com.cps.common.servcies.BaseHttpService;
import com.cps.cpsApi.packet.CpsMemberPacket;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 링크프라이스 광고주 API
 * @date 2024-09-03
 */

@Slf4j
@Service
public class HttpService extends BaseHttpService {
    public static final String DOMAIN = "";

    public HttpService() {
        super(DOMAIN);
    }

    public HttpService(String domain) {
        super(domain);
    }

    public HttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public HttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> SendLinkPriceMerchant(CpsMemberPacket.MemberInfo.Domain request) {
        try{
            var result = this.PostAsync(request.getDomain(), request, String.class);
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> responseObj = new ObjectMapper().readValue(result.block(), new TypeReference<List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse>>() {});
            return responseObj;
        } catch (Exception ex) {
            log.error("SendLinkPriceMerchant : {} ", ex);
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> errorResult = new ArrayList<>();
            errorResult.get(0).setResultCode("9999");
            return errorResult;
        }
    }
}
