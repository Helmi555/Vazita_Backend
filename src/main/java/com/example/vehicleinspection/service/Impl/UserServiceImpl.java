package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.repository.UserRepository;
import com.example.vehicleinspection.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
