package com.example.vehicleinspection.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Id is necessary")
    private String id;
    @NotBlank(message = "Password is necessary")
    private String password;

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
