package com.mobcomms.sample.service;

import com.mobcomms.common.servcies.BaseHttpService;
import com.mobcomms.sample.model.PayboocPacket;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;


@Service
public class PayboocHttpService extends BaseHttpService {
    public PayboocHttpService() {
        super(PayboocPacket.DOMAIN);
    }

    public PayboocHttpService(String domain) {
        super(domain);
    }

    public PayboocHttpService(String domain, Consumer<HttpHeaders> headersConsumer) {
        super(domain, headersConsumer);
    }

    public PayboocHttpService(String domain, Consumer<HttpHeaders> headersConsumer, Consumer<List<ExchangeFilterFunction>> filtersConsumer) {
        super(domain, headersConsumer, filtersConsumer);
    }

    public PayboocPacket.GetUserInfo.Response GetUserInfo(PayboocPacket.GetUserInfo.Requset requset){
        try{
            var result = this.GetAsync(PayboocPacket.GET_USERINFO_ENDPOINT,requset,PayboocPacket.GetUserInfo.Response.class);
            return result.block();
        } catch (Exception ex){
            var error = new PayboocPacket.GetUserInfo.Response();
            error.setResult_code("-9999");
            error.setResult_message(ex.getMessage());
            return error;
        }
    }

    public PayboocPacket.PostUserInfo.Response PostUserInfo(PayboocPacket.PostUserInfo.Requset request){

        try{
            var result = this.PostAsync(PayboocPacket.GET_USERINFO_ENDPOINT,request,PayboocPacket.PostUserInfo.Response.class);
            return result.block();
        } catch (Exception ex){
            var error = new PayboocPacket.PostUserInfo.Response();
            error.setResult_code("-9999");
            error.setResult_message(ex.getMessage());
            return error;
        }
    }

    public PayboocPacket.PostUserInfo.Response PostFormUserInfo(PayboocPacket.PostUserInfo.Requset request) {
        try{
            var formData = BodyInserters
                    .fromFormData("adid", request.getAdid())
                    .with("os", request.getOs());

            var result = this.PostFormAsync(PayboocPacket.GET_USERINFO_ENDPOINT,formData,PayboocPacket.PostUserInfo.Response.class);
            return result.block();
        } catch (Exception ex){
            var error = new PayboocPacket.PostUserInfo.Response();
            error.setResult_code("-9999");
            error.setResult_message(ex.getMessage());
            return error;
        }
    }

    //비동기 방식 처리.
    public Mono<PayboocPacket.GetUserInfo.Response> GetUserInfoAsync(PayboocPacket.GetUserInfo.Requset request){
        try {
            return this.GetAsync(PayboocPacket.GET_USERINFO_ENDPOINT, request, PayboocPacket.GetUserInfo.Response.class)
                    .onErrorResume(ex -> {
                        PayboocPacket.GetUserInfo.Response error = new PayboocPacket.GetUserInfo.Response();
                        error.setResult_code("-9000");
                        error.setResult_message(ex.getMessage());
                        return Mono.just(error);
                    });
        } catch (Exception e) {
            PayboocPacket.GetUserInfo.Response error = new PayboocPacket.GetUserInfo.Response();
            error.setResult_code("-9999");
            error.setResult_message(e.getMessage());
            return Mono.just(error);
        }
    }
}

