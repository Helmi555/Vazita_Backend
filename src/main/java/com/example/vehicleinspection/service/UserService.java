package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.response.UserResponse;

public interface UserService {
    UserResponse getUser(String username);
}
