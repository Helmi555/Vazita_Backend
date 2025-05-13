package com.example.vehicleinspection.controller;


import com.example.vehicleinspection.dto.response.UserResponse;
import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.service.UserService;
import com.example.vehicleinspection.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username= jwtUtils.extractUsername(token);
        if(username == null || username.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        UserResponse userResponse=userService.getUser(username);
        return ResponseEntity.ok(Map.of("data",userResponse));
    }

}
