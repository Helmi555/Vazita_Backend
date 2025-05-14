package com.example.vehicleinspection.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.central")
    public DataSourceProperties centralProps() {
        return new DataSourceProperties();
    }

    @Bean(name = "centralDataSource")
    public DataSource centralDataSource() {
        return centralProps().initializeDataSourceBuilder().build();
    }

    @Bean(name = "routingDataSource")
    @Primary
    public DynamicRoutingDataSource routingDataSource(DataSource centralDataSource) {
        return new DynamicRoutingDataSource(centralDataSource);
    }


    @Bean(name = "dataSource")
    public DataSource dataSource(DynamicRoutingDataSource routingDataSource) {
        return routingDataSource;
    }
}