package com.example.vehicleinspection.controller;


import com.example.vehicleinspection.dto.request.LoginRequest;
import com.example.vehicleinspection.dto.response.LoginResponse;
import com.example.vehicleinspection.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login endpoint", description = "Authenticates a user and returns a JWT token")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
