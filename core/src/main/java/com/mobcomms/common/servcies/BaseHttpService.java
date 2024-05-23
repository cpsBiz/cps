package com.mobcomms.common.servcies;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;


public class BaseHttpService {
    private final WebClient webClient;

    public BaseHttpService(String domain) {
        this(domain, defaultHeadersConsumer(), defaultFiltersConsumer());
    }

    public BaseHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        this(domain, headersConsumer, defaultFiltersConsumer());
    }

    public BaseHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        this.webClient = createWebClientBuilder(domain, headersConsumer, filtersConsumer).build();
    }

    private WebClient.Builder createWebClientBuilder(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(10)) // 타임아웃 설정
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10)));

        return WebClient.builder()
                .baseUrl(domain)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filters(filtersConsumer)
                .defaultHeaders(headersConsumer);
    }

    private static Consumer<HttpHeaders> defaultHeadersConsumer() {
        return headers -> {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        };
    }

    private static Consumer<List<ExchangeFilterFunction>> defaultFiltersConsumer() {
        return filters -> {
            filters.add(logRequest());
            filters.add(logResponse());
        };
    }

    // 요청 로깅 필터
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientRequest);
        });
    }

    // 응답 로깅 필터
    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("Response: " + clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientResponse);
        });
    }

    //Get 요청 Requset Model, Response Model

    ///public

    //Post 요청

    //Put 요청

    //Delete 요청
}
