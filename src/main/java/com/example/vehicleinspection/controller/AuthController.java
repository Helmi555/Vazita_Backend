package com.example.vehicleinspection.controller;


import com.example.vehicleinspection.dto.request.LoginRequest;
import com.example.vehicleinspection.dto.response.LoginResponse;
import com.example.vehicleinspection.jwt.JwtBlacklistService;
import com.example.vehicleinspection.service.AuthService;
import com.example.vehicleinspection.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final JwtBlacklistService jwtBlacklistService;

    public AuthController(AuthService authService, JwtUtils jwtUtils, JwtBlacklistService jwtBlacklistService) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login endpoint", description = "Authenticates a user and returns a JWT token")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(summary ="user logout")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtBlacklistService.blacklistToken(token);
            return ResponseEntity.ok(Map.of("data","Logged out successfully"));
        }
        return ResponseEntity.badRequest().build();
    }
}
