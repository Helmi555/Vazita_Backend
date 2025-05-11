package com.example.vehicleinspection.config.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private final CentralDataSourceProperties centralProps;
    private final LocalDataSourceProperties localProps;

    public DataSourceConfig(CentralDataSourceProperties centralProps, LocalDataSourceProperties localProps) {
        this.centralProps = centralProps;
        this.localProps = localProps;
    }

    @Bean
    @Primary
    public DataSource centralDataSource() {
        return DataSourceBuilder.create()
                .url(centralProps.getUrl())
                .username(centralProps.getUsername())
                .password(centralProps.getPassword())
                .driverClassName(centralProps.getDriverClassName())
                .build();
    }

    @Bean
    public DataSource dynamicDataSource(DataSource centralDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("central", centralDataSource);

        DynamicDataSource ds = new DynamicDataSource();
        ds.setTargetDataSources(targetDataSources);
        ds.setDefaultTargetDataSource(centralDataSource);
        ds.afterPropertiesSet();
        return ds;
    }
}