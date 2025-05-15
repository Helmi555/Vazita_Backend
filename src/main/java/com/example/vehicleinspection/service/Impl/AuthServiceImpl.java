package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.request.LoginRequest;
import com.example.vehicleinspection.dto.response.LoginResponse;
import com.example.vehicleinspection.exception.AccessMemberDeniedException;
import com.example.vehicleinspection.model.CentreCVT;
import com.example.vehicleinspection.model.Group;
import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.repository.CentreCVTRepository;
import com.example.vehicleinspection.repository.GroupRepository;
import com.example.vehicleinspection.repository.UserRepository;
import com.example.vehicleinspection.service.AuthService;
import com.example.vehicleinspection.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final CentreCVTRepository centreCVTRepository;
    private final JwtUtils jwtUtils;
    private final static Logger logger= LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserRepository userRepository, GroupRepository groupRepository, CentreCVTRepository centreCVTRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.centreCVTRepository = centreCVTRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user =userRepository.findById(loginRequest.getId()).orElseThrow(
                ()->new UsernameNotFoundException("User not found")
        );

        if(!user.isValid()){
            throw new AccessMemberDeniedException("Access denied");
        }
        if(!loginRequest.getPassword().matches(user.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }
        Group group=groupRepository.findById(user.getCodGrp()).orElseThrow(
                ()->new UsernameNotFoundException("Group not found")
        );

        CentreCVT centreCVT=centreCVTRepository.findById(user.getIdCentre()).orElseThrow(
                ()->new UsernameNotFoundException("Centre CVT not found")
        );

        logger.info("User to create {}",user);
        String token=jwtUtils.generateJwtToken(user);
        logger.info("Token created is {}",token);
        LoginResponse loginResponse=new LoginResponse(
                token,
                user.getIdUser(),
                centreCVT.getIdCentre(),
                group.getDesignation()
        );
        return loginResponse;

    }

    @Override
    public void logout(String username, String token) {

    }
}
