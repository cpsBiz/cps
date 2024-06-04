package com.mobcomms.adPanel.service;

import com.mobcomms.adPanel.dto.AdPanelDto;
import com.mobcomms.adPanel.entity.AdPanelEntity;
import com.mobcomms.adPanel.repository.AdPanelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional("transactionManager")
/*@RequiredArgsConstructor*/
public class AdPanelService {

    private final WebClient webClient;
    private final AdPanelRepository adPanelRepository;
    private final String url = "https://www.mobwithad.com";

    public AdPanelService(WebClient.Builder webClientBuilder, AdPanelRepository adPanelRepository) {
        this.webClient = webClientBuilder.baseUrl(url).build();
        this.adPanelRepository = adPanelRepository;
    }

    public List<AdPanelDto> adPanelList(){
        return adPanelRepository.findAll().stream().map(entity -> AdPanelDto.adPanelList(entity)).collect(Collectors.toList());
    }

    //dto로 받고
    public void adPanelInsert(AdPanelDto dto){
        AdPanelEntity entity = AdPanelDto.toAdPanelEntity(dto);
        List<AdPanelEntity> adPanelSize = adPanelRepository.findAllByClientCodeAndProductCodeAndZoneIdAndOsType(entity.getClientCode(), entity.getProductCode(), entity.getZoneId(), entity.getOsType());
        if (adPanelSize.size() == 0) {
            entity.setRegDttm(LocalDateTime.now());
        } else {
            entity.setModDttm(LocalDateTime.now());
        }
        adPanelRepository.save(entity);
    }

    public void adPanelDelete(AdPanelDto dto){
        AdPanelEntity entity = AdPanelDto.toAdPanelEntity(dto);
        AdPanelDto.adPanelList(entity);
        adPanelRepository.delete(entity);
    }

    public Mono<String> panelData(String uri) {
        return this.webClient.get()
                .uri(uri)
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
