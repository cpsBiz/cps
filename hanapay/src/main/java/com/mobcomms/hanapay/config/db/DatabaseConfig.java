package com.mobcomms.hanapay.config.db;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


public abstract class DatabaseConfig {
    protected void configureDataSource(DataSource dataSource, DatabaseProperties databaseProperties) {
		dataSource.setDriverClassName(databaseProperties.getDriverClassName());
		dataSource.setUrl(databaseProperties.getUrl());
		dataSource.setUsername(databaseProperties.getUsername());
		dataSource.setPassword(databaseProperties.getPassword());
		dataSource.setMaxActive(databaseProperties.getMaxActive());
		dataSource.setMaxIdle(databaseProperties.getMaxIdle());
		dataSource.setMinIdle(databaseProperties.getMinIdle());
		dataSource.setMaxWait(databaseProperties.getMaxWait());
		dataSource.setMaxAge(databaseProperties.getMaxAge());
		dataSource.setTestOnBorrow(databaseProperties.isTestOnBorrow());
		dataSource.setTestWhileIdle(databaseProperties.isTestWhileIdle());
		dataSource.setTimeBetweenEvictionRunsMillis(databaseProperties.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(databaseProperties.getMinEvictableIdleTimeMillis());
		dataSource.setValidationQuery(databaseProperties.getValidationQuery());
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setRemoveAbandonedTimeout(databaseProperties.getRemoveAbandonedTimeout());
		dataSource.setLogAbandoned(databaseProperties.isLogAbandoned());
    }
}

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MariaDatabaseProperties.class)
class MariaDatabaseConfig extends DatabaseConfig {
	private final DatabaseProperties mariaDatabaseProperties;

	@Bean(name = "mariaDataSource")
	public DataSource dataSource() {
		DataSource mariaDatasource = new DataSource();
		configureDataSource(mariaDatasource, mariaDatabaseProperties);
		return mariaDatasource;
	}

	@Bean(name = "mariaTransactionManager")
	public PlatformTransactionManager mariaTransactionManager(@Qualifier("mariaDataSource") DataSource mariaDataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(mariaDataSource);
		transactionManager.setGlobalRollbackOnParticipationFailure(false);
		return transactionManager;
	}
}
