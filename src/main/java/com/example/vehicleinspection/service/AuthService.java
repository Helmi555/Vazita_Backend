package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.request.LoginRequest;
import com.example.vehicleinspection.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    void logout(String username, String token);

    interface DossierDefautService {
    }
}
