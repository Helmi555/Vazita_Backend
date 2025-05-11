package com.example.vehicleinspection.dto.response;

import com.example.vehicleinspection.model.enums.Role;

public class LoginResponse {

    private String token;
    private String username;
    private Integer id_centre;
    private Role role;

    public LoginResponse() {
    }

    public LoginResponse(String token, String username, Integer id_centre, Role role) {
        this.token = token;
        this.username = username;
        this.id_centre = id_centre;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId_centre() {
        return id_centre;
    }

    public void setId_centre(Integer id_centre) {
        this.id_centre = id_centre;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
