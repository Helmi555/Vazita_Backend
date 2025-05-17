package com.example.vehicleinspection.controller;


import com.example.vehicleinspection.dto.request.UserRequest;
import com.example.vehicleinspection.dto.response.UserResponse;
import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.service.UserService;
import com.example.vehicleinspection.util.JwtUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private static  final Logger logger= LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId= jwtUtils.extractIdUser(token);
        if(userId == null || userId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("User id is {}",userId);
        UserResponse userResponse=userService.getUser(userId);
        return ResponseEntity.ok(Map.of("data",userResponse));
    }


    @PreAuthorize("hasAnyRole('ADMIN','INSPECTOR')")
    @PostMapping("")
    public ResponseEntity<?>createUser(@Valid @RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return ResponseEntity.ok().body(Map.of("message","user created sucessfully"));
    }
}
