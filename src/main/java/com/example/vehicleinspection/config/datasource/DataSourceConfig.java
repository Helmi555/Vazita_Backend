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
    public DataSourceProperties centralProps() {
        return new DataSourceProperties();
    }

    @Bean(name="centralDataSource")
    public DataSource centralDataSource() {
        return centralProps().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.local1")
    public DataSourceProperties local1Props() { return new DataSourceProperties(); }
    @Bean(name="local1DataSource")
    public DataSource local1DataSource() {
        return local1Props().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.local2")
    public DataSourceProperties local2Props() { return new DataSourceProperties(); }
    @Bean(name="local2DataSource")
    public DataSource local2DataSource() {
        return local2Props().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public DataSource routingDataSource(
            @Qualifier("centralDataSource") DataSource central,
            @Qualifier("local1DataSource") DataSource l1,
            @Qualifier("local2DataSource") DataSource l2) {

        DynamicRoutingDataSource routing = new DynamicRoutingDataSource();
        Map<Object,Object> map = new HashMap<>();
        map.put("CENTRAL", central);
        map.put("10",       l1);
        map.put("20",       l2);
        routing.setTargetDataSources(map);
        routing.setDefaultTargetDataSource(central);
        return routing;
    }
}
