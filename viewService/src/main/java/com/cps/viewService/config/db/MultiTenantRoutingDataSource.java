package com.cps.viewService.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }
}