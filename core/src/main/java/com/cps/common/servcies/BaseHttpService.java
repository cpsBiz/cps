package com.cps.common.servcies;

import com.cps.common.utils.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
public abstract class BaseHttpService {
    private WebClient webClient;

    public BaseHttpService() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    // 요청 로깅 필터
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            return Mono.deferContextual(contextView->{
                String processId = contextView.getOrDefault("processId", "N/A");
                final StringBuilder sb = new StringBuilder();
                sb.append("Requset INFO[").append(processId).append("]").append(System.lineSeparator());
                sb.append("Method : ").append(clientRequest.method()).append(System.lineSeparator());
                sb.append("Url : ").append(clientRequest.url()).append(System.lineSeparator());
                sb.append("HeaderInfo").append(System.lineSeparator());
                clientRequest.headers().forEach((name, values) -> values.forEach(value -> sb.append(name + ": " + value).append(System.lineSeparator())));
                log.info(sb.toString());
                return Mono.just(clientRequest);
            });
        });
    }

    // 응답 로깅 필터
    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            return Mono.deferContextual(contextView-> {
                String processId = contextView.getOrDefault("processId", "N/A");
                final StringBuilder sb = new StringBuilder();
                sb.append("Response INFO[").append(processId).append("]").append(System.lineSeparator());
                sb.append("StatusCode : ").append(clientResponse.statusCode()).append(System.lineSeparator());
                sb.append("HeaderInfo").append(System.lineSeparator());
                clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> sb.append(name + ": " + value).append(System.lineSeparator())));
                log.info(sb.toString());
                return Mono.just(clientResponse);
            });
        });
    }

    public WebClient getWebClient() {
        return this.webClient;
    }

    //Get 요청
    public <T>Mono<T> GetAsync(String endPoint, Object requestData, Class<T> responseType) throws Exception {
        String endPointWithQuery = endPoint;

        if(requestData != null){
             endPointWithQuery = endPoint + "?" + CommonUtil.objectToQueryString(requestData);
        }

        return this.webClient.get()
                .uri(endPointWithQuery)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    //Get 요청
    public <T>Mono<T> GetAsync(String url, Class<T> responseType,Consumer<HttpHeaders> headersConsumer) throws Exception {
        /*
        String endPointWithQuery = endPoint;

        if(requestData != null){
            endPointWithQuery = endPoint + "?" + CommonUtil.objectToQueryString(requestData);
        }
        */

        return this.webClient.get()
                .uri(url)
                .headers(headersConsumer)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    //post 요청
    public <T> Mono<T> PostFormAsync(String endPoint, BodyInserters.FormInserter<String> requestData, Class<T> responseType) throws Exception {
        LogRequestData(requestData);
        Consumer<HttpHeaders> headersConsumer = headers -> {
            headers.set("Custom-Header", "HeaderValue");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // 다른 헤더 설정
        };

        return this.webClient.post()
                .uri(endPoint)
                .headers(headersConsumer)
                .body(requestData)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    //post 요청
    public <T>Mono<T> PostAsync(String endPoint,Object requestData, Class<T> responseType) throws Exception {
        LogRequestData(requestData);
        return this.webClient.post()
                .uri(endPoint)
                .bodyValue(requestData)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    //post 요청
    public <T>Mono<T> PostAsync(String endPoint,Object requestData, Class<T> responseType, Consumer<HttpHeaders> headersConsumer) throws Exception {
        LogRequestData(requestData);
        return this.webClient.post()
                .uri(endPoint)
                .bodyValue(requestData)
                .headers(headersConsumer)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    //Put 요청
    public <T>Mono<T> PutAsync(String endPoint,Object requestData, Class<T> responseType) throws Exception {
        return this.webClient.put()
                .uri(endPoint)
                .bodyValue(BodyInserters.fromValue(requestData))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    //Delete 요청
    public <T>Mono<T> DeleteAsync(String endPoint,Object requestData, Class<T> responseType) throws Exception {
        String endPointWithQuery = endPoint + "?" + CommonUtil.objectToQueryString(requestData);
        return this.webClient.delete()
                .uri(endPointWithQuery)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    //patch 요청
    public <T>Mono<T> PatchAsync(String endPoint,Object requestData, Class<T> responseType) throws Exception{
        return this.webClient.patch()
                .uri(endPoint)
                .bodyValue(BodyInserters.fromValue(requestData))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 4xx :" +  errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 4xx :" + errorMessage));
                                })
                ).onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorMessage -> {
                                    //log.error("API ClientError 5xx : " + errorMessage);
                                    return Mono.error(new RuntimeException("ClientError 5xx : " + errorMessage));
                                })
                ).bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    log.error("API WebClientResponseException" + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API WebClientResponseException", ex));
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("API Exception " + System.lineSeparator() + ex);
                    return Mono.error(new RuntimeException("API Exception : " + ex.getMessage()));
                });
    }

    private void LogRequestData(Object requestData){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("RequestData : " ).append(System.lineSeparator());
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestData);
            sb.append(jsonString);
            log.info(sb.toString());
        } catch (JsonProcessingException e) {

        }
    }
}
