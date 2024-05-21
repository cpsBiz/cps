package com.mobcomms.sample.service;

import com.mobcomms.sample.dto.SampleDto;
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
    private final String TEST_URL = "http://localhost:8080";
    private final String TEST_URI = "/api/1";

    public WebClientSampleService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(TEST_URL).build();
    }

    public Mono<List<SampleDto>> sampleData() {
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
                .bodyToMono(new ParameterizedTypeReference<List<SampleDto>>() {})
                .doOnError(WebClientResponseException.class, ex ->
                        log.error("Status code: " + ex.getStatusCode())
                )
                .doOnError(Exception.class, ex ->
                        log.error("An error occurred: " + ex.getMessage())
                );
    }
}
