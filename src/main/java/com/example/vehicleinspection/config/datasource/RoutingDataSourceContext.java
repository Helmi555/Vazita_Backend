package com.example.vehicleinspection.config.datasource;

public class RoutingDataSourceContext {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setDataSource(String idCentre) {
        CONTEXT.set("local_" + idCentre);
    }

    public static String getDataSource() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}