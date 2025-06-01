package com.example.vehicleinspection.dto.response;

public class MarqueResponse {
    private String desiGL;
    private Long count;

    public String getDesiGL() {
        return desiGL;
    }

    public void setDesiGL(String desiGL) {
        this.desiGL = desiGL;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public MarqueResponse(String desiGL, Long count) {
        this.desiGL = desiGL;
        this.count = count;
    }

    public MarqueResponse() {
    }
}
