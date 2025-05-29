package com.example.vehicleinspection.service;

import com.example.vehicleinspection.dto.request.UserRequest;
import com.example.vehicleinspection.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserMe(String username);

    void createUser(UserRequest userRequest);

    List<UserResponse> getUsers(String userId);

    void updateUser(String userId, UserRequest userRequest);

    void deleteUser(String userId);
}
