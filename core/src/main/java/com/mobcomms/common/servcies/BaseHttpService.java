package com.mobcomms.common.servcies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobcomms.common.utils.CommonUtil;
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

        //TODO ExchangeStrategies 커스텀으로 받을지는 추후 확장.
        //codec 처리를 위한 in-memory buffer 값이 256KB로 기본설정 256KB보다 큰 HTTP 메시지를 처리하기 위한 설정 추가.
        ExchangeStrategies exchangeStrategies =
                ExchangeStrategies
                        .builder()
                        .codecs(configurer -> configurer.defaultCodecs()
                                .maxInMemorySize(1024*1024*50))
                        .build();

        exchangeStrategies
                .messageWriters().stream()
                .filter(LoggingCodecSupport.class::isInstance)
                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));

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
            filters.add(addProcessId());
            filters.add(logRequest());
            filters.add(logResponse());
        };
    }

    private static ExchangeFilterFunction addProcessId() {
        return (request, next) -> {
            String processId = UUID.randomUUID().toString();
            return next.exchange(request)
                    .contextWrite(Context.of("processId", processId));
        };
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
    public <T>Mono<T> GetAsync(String endPoint, Object requestData, Class<T> responseType,Consumer<HttpHeaders> headersConsumer) throws Exception {
        String endPointWithQuery = endPoint;

        if(requestData != null){
            endPointWithQuery = endPoint + "?" + CommonUtil.objectToQueryString(requestData);
        }

        return this.webClient.get()
                .uri(endPointWithQuery)
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
    public <T>Mono<T> PostFormAsync(String endPoint,BodyInserters.FormInserter<String> requestData, Class<T> responseType) throws Exception {
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
