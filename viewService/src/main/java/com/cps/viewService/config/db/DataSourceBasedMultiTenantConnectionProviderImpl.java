package com.cps.viewService.config.db;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private final AbstractRoutingDataSource routingDataSource;

    @Autowired
    public DataSourceBasedMultiTenantConnectionProviderImpl(AbstractRoutingDataSource routingDataSource) {
        this.routingDataSource = routingDataSource;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return (DataSource) routingDataSource.getResolvedDefaultDataSource();
    }

    @Override
    protected DataSource selectDataSource(Object tenantIdentifier) {
        return (DataSource) routingDataSource.getResolvedDataSources().get(tenantIdentifier);
    }
}