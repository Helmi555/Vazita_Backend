package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.request.UserRequest;
import com.example.vehicleinspection.dto.response.UserResponse;

import java.net.URI;

public interface UserService {
    UserResponse getUser(String username);

    void createUser(UserRequest userRequest);
}
