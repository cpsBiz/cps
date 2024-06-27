package com.mobcomms.shinhan.service;

import com.mobcomms.common.servcies.BaseHttpService;
import com.mobcomms.shinhan.dto.packet.MobwithPacket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import java.util.List;
import java.util.function.Consumer;

/*
 * Created by enliple
 * Create Date : 2024-06-27
 * Class 설명, method
 * UpdateDate : 2024-06-27, 업데이트 내용
 */


@Service
public class MobwithHttpService  extends BaseHttpService {

    public static final String DOMAIN = "https://dev.mobwithad.com";

    public MobwithHttpService() {
        super(DOMAIN);
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



}
