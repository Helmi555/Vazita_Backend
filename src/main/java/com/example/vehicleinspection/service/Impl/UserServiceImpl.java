package com.example.vehicleinspection.service.Impl;

import com.example.vehicleinspection.dto.request.UserRequest;
import com.example.vehicleinspection.dto.response.UserResponse;
import com.example.vehicleinspection.exception.ElementNotFoundException;
import com.example.vehicleinspection.model.CentreCVT;
import com.example.vehicleinspection.model.Group;
import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.model.enums.Role;
import com.example.vehicleinspection.repository.CentreCVTRepository;
import com.example.vehicleinspection.repository.GroupRepository;
import com.example.vehicleinspection.repository.UserRepository;
import com.example.vehicleinspection.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public UserResponse getUserMe(String username) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new ElementNotFoundException("User not found")
        );
        Group group = groupRepository.findById(user.getCodGrp()).orElseThrow(
                () -> new ElementNotFoundException("Group not found")
        );

        CentreCVT centreCVT = centreCVTRepository.findById(user.getIdCentre()).orElseThrow(
                () -> new ElementNotFoundException("Centre CVT not found")
        );
        return UserResponse.userToResponseDto(user);
    }

    @Override
    @Transactional
    public void createUser(UserRequest userRequest) {
        logger.info("We are in the create user with {}", userRequest);
        if(userRepository.existsById(userRequest.getIdUser())){
            throw new ElementNotFoundException("User already exists in system");
        }
        if(!groupRepository.existsById(Role.fromRole(userRequest.getDesignation()))){
            throw new ElementNotFoundException("Group does not exist in system");
        }
        if(!centreCVTRepository.existsById(userRequest.getIdCentre())){
            throw new ElementNotFoundException("Centre does not exist in system");
        }

        logger.info("User to be saved : " + userRequest);

        User user = new User(
                userRequest.getIdUser(),
                userRequest.getPassword(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getFirstNameA(),
                userRequest.getLastNameA(),
                userRequest.getStartDate(),
                userRequest.getEndDate(),
                userRequest.getStatus(),
                Role.fromRole(userRequest.getDesignation()),
                userRequest.getIdCentre()
        );

        user=userRepository.save(user);


        logger.info("User saved : " + user);
    }

    @Override
    public List<UserResponse> getUsers(String userId) {
        User user=userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        List<User> users=userRepository.findAllByIdCentre(user.getIdCentre());
        return users.stream().map(UserResponse::userToResponseDto).toList();
    }

    @Override
    @Transactional
    public void updateUser(String userId, UserRequest userRequest) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new UsernameNotFoundException("User doesnt exist in system")
        );
        if(!groupRepository.existsById(Role.fromRole(userRequest.getDesignation()))){
            throw new UsernameNotFoundException("Group does not exist in system");
        }
        if(!centreCVTRepository.existsById(userRequest.getIdCentre())){
            throw new UsernameNotFoundException("Centre does not exist in system");
        }

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getFirstName() != null && !userRequest.getFirstName().isBlank()) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null && !userRequest.getLastName().isBlank()) {
            user.setLastName(userRequest.getLastName());
        }
        if (userRequest.getFirstNameA() != null && !userRequest.getFirstNameA().isBlank()) {
            user.setFirstNameA(userRequest.getFirstNameA());
        }
        if (userRequest.getLastNameA() != null && !userRequest.getLastNameA().isBlank()) {
            user.setLastNameA(userRequest.getLastNameA());
        }
        if (userRequest.getStartDate() != null) {
            user.setStartDate(userRequest.getStartDate());
        }
        if (userRequest.getEndDate() != null) {
            user.setEndDate(userRequest.getEndDate());
        }
        if (userRequest.getStatus() != null && !userRequest.getStatus().isBlank()) {
            user.setStatus(userRequest.getStatus());
        }
        if (userRequest.getDesignation() != null && Role.isValid(userRequest.getDesignation().name())) {
            user.setCodGrp(Role.fromRole(userRequest.getDesignation()));
        }
        if (userRequest.getIdCentre() != null) {
            user.setIdCentre(userRequest.getIdCentre());
        }

        logger.info("User to be updated : {}", userRequest);
        user=userRepository.save(user);
        logger.info("User updated : {}" ,user);

    }

    @Override
    public void deleteUser(String userId) {
        if(!userRepository.existsById(userId)){
            throw new UsernameNotFoundException("User doesnt exist in system");
        }
        userRepository.deleteById(userId);
    }

}
