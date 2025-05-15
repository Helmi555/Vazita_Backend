package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.response.UserResponse;
import com.example.vehicleinspection.model.CentreCVT;
import com.example.vehicleinspection.model.Group;
import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.repository.CentreCVTRepository;
import com.example.vehicleinspection.repository.GroupRepository;
import com.example.vehicleinspection.repository.UserRepository;
import com.example.vehicleinspection.service.UserService;
import com.example.vehicleinspection.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final CentreCVTRepository centreCVTRepository;
    private final static Logger logger= LoggerFactory.getLogger(AuthServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, GroupRepository groupRepository, CentreCVTRepository centreCVTRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.centreCVTRepository = centreCVTRepository;
    }

    @Override
    public UserResponse getUser(String username) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        Group group = groupRepository.findById(user.getCodGrp()).orElseThrow(
                () -> new UsernameNotFoundException("Group not found")
        );

        CentreCVT centreCVT = centreCVTRepository.findById(user.getIdCentre()).orElseThrow(
                () -> new UsernameNotFoundException("Centre CVT not found")
        );
        return UserResponse.userToResponseDto(user);
    }

}
