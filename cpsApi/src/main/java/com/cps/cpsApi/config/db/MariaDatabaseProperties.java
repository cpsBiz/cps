package com.cps.cpsApi.config.db;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.maria.datasource")
@Data
public class MariaDatabaseProperties implements DatabaseProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int initialSize;
    private int maxActive;
    private int maxIdle;
    private int minIdle;
    private int maxWait;
    private int maxAge;
    private boolean testOnBorrow;
    private boolean testWhileIdle;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private int removeAbandonedTimeout;
    private boolean logAbandoned;
    private String validationQuery;
}