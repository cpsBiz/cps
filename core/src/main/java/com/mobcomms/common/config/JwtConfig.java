package com.mobcomms.common.config;

import org.springframework.stereotype.Component;


@Component
public class JwtConfig {
    private String secret = "defaultSecret";
    private long expiration = 3600;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}