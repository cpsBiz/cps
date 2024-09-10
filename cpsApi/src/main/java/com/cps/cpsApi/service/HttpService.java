package com.cps.cpsApi.service;

import com.cps.common.servcies.BaseHttpService;
import com.cps.cpsApi.packet.CpsMemberPacket;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
            var result = linkPriceDetail(request.getDomain());
            //var result = this.PostAsync(request.getDomain(), request, String.class);
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> responseObj = new ObjectMapper().readValue(result, new TypeReference<List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse>>() {});
            return responseObj;
        } catch (Exception ex) {
            log.error("SendLinkPriceMerchant : {} ", ex);
            List<CpsMemberPacket.MemberInfo.LinkPriceAgencyResponse> errorResult = new ArrayList<>();
            return errorResult;
        }
    }

    public  String linkPriceDetail(String Domain) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;
        String responseLine;

        try {
            URL url = new URL(Domain);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return response.toString();
    }
}
