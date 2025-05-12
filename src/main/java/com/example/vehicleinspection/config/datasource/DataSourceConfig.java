package com.example.vehicleinspection.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.central")
    public DataSourceProperties centralDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.local")
    public DataSourceProperties localDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "centralDataSource")
    public DataSource centralDataSource() {
        return centralDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "localDataSource")
    public DataSource localDataSource() {
        return localDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    public DataSource routingDataSource(@Qualifier("centralDataSource") DataSource central,
                                        @Qualifier("localDataSource") DataSource local) {
        DynamicRoutingDataSource routing = new DynamicRoutingDataSource();
        Map<Object, Object> sources = new HashMap<>();
        sources.put("CENTRAL", central);
        sources.put("1", local); // centre ID 1 => localDataSource
        routing.setTargetDataSources(sources);
        routing.setDefaultTargetDataSource(central);
        return routing;
    }
}