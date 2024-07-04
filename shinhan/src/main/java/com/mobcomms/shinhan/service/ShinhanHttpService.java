package com.mobcomms.shinhan.service;

import com.mobcomms.common.servcies.BaseHttpService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.util.List;
import java.util.function.Consumer;

/*
 * Created by enliple
 * Create Date : 2024-07-04
 * Class 설명, method
 * UpdateDate : 2024-07-04, 업데이트 내용
 */
public class ShinhanHttpService extends BaseHttpService {
    public static final String DOMAIN = "https://api-gateway.coupang.com";
    public ShinhanHttpService() {
        super(DOMAIN);
    }

    public ShinhanHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public ShinhanHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }
}
