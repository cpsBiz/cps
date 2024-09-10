package com.cps.cpsApi.config.db;

public interface DatabaseProperties {
    String getDriverClassName();
    String getUrl();
    String getUsername();
    String getPassword();
    int getInitialSize();
    int getMaxActive();
    int getMaxIdle();
    int getMinIdle();
    int getMaxWait();
    int getMaxAge();
    boolean isTestOnBorrow();
    boolean isTestWhileIdle();
    int getTimeBetweenEvictionRunsMillis();
    int getMinEvictableIdleTimeMillis();
    int getRemoveAbandonedTimeout();
    boolean isLogAbandoned();
    String getValidationQuery();
}
