package com.mobcomms.cgvSupport.config.db;

public interface DatabaseProperties {
    String getDriverClassName();
    String getUrl();
    String getUsername();
    String getPassword();
    int getMaxActive();
    int getMaxIdle();
    int getMinIdle();
    int getMaxWait();
}