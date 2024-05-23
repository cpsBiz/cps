package com.mobcomms.sample.service;

import com.mobcomms.sample.dto.SampleDto;
import com.mobcomms.sample.repository.SampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class WebClientSampleService {

    private final WebClient webClient;
    private final SampleRepository sampleRepository;
    private final String TEST_URL = "http://localhost:8080";
    private final String TEST_URI = "/api/1";
    //private final String TEST_URL = "https://dev.mobwithad.com/api/banner/app/vp/v1/paybooc";
    //private final String TEST_URI = "?zone=123124&count=1&w=250&h=250&adid=dsfadsfadsf&os_type=aos&is_optout=0";


    public WebClientSampleService(WebClient.Builder webClientBuilder, SampleRepository sampleRepository) {
        this.webClient = webClientBuilder.baseUrl(TEST_URL).build();
        this.sampleRepository = sampleRepository;
    }

    public Mono<String> sampleData() {
        return this.webClient.get()
                .uri(TEST_URI)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    log.error("4xx Error: {}", errorMessage);
                                    return Mono.error(new RuntimeException("Client error: " + errorMessage));
                                })
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    log.error("5xx Error: {}", errorMessage);
                                    return Mono.error(new RuntimeException("Server error: " + errorMessage));
                                })
                )
                .bodyToMono(new ParameterizedTypeReference<String>() {})
                .doOnError(WebClientResponseException.class, ex ->
                        log.error("Status code: " + ex.getStatusCode())
                )
                .doOnError(Exception.class, ex ->
                        log.error("An error occurred: " + ex.getMessage())
                );
    }
}
