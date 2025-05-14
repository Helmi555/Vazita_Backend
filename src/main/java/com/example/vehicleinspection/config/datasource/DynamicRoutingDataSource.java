package com.example.vehicleinspection.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    private final DataSource defaultTargetDataSource;
    private final Map<Object, Object> resolvedDataSources = new ConcurrentHashMap<>();

    public DynamicRoutingDataSource(DataSource defaultTarget) {
        this.defaultTargetDataSource = defaultTarget;
        super.setDefaultTargetDataSource(defaultTarget);
        super.setTargetDataSources(resolvedDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String key = RoutingDataSourceContext.getDataSource();
        return (key != null && resolvedDataSources.containsKey(key)) ? key : null;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        Object lookupKey = determineCurrentLookupKey();
        return (lookupKey == null)
                ? defaultTargetDataSource
                : (DataSource) resolvedDataSources.get(lookupKey);
    }

    public void addDataSource(String key, DataSource ds) {
        resolvedDataSources.put(key, ds);
        super.setTargetDataSources(resolvedDataSources);
        super.afterPropertiesSet();
    }

    public boolean containsDataSource(String key) {
        return resolvedDataSources.containsKey(key);
    }
}