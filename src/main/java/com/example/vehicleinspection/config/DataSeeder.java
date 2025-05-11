package com.example.vehicleinspection.config;

import com.example.vehicleinspection.model.CentreCVT;
import com.example.vehicleinspection.model.Group;
import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.model.enums.Role;
import com.example.vehicleinspection.repository.CentreCVTRepository;
import com.example.vehicleinspection.repository.GroupRepository;
import com.example.vehicleinspection.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Configuration
public class DataSeeder {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CentreCVTRepository centreCVTRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(GroupRepository groupRepository, UserRepository userRepository, CentreCVTRepository centreCVTRepository, PasswordEncoder passwordEncoder) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.centreCVTRepository = centreCVTRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner seedDb() {
        return args -> {
            seedRoles();
            seedCentres();
            seedUsers();
        };
    }

    private void seedRoles() {
        if (groupRepository.count() == 0) {
            groupRepository.saveAll(List.of(
                    new Group(1, Role.ROLE_ADMIN),
                    new Group(2, Role.ROLE_INSPECTOR),
                    new Group(3, Role.ROLE_ADJOINT)
            ));
        }
    }

    private void seedCentres() {
        if (centreCVTRepository.count() == 0) {
            centreCVTRepository.saveAll(List.of(
                    new CentreCVT(1, "admin", "pass", "localhost", "cvtx1"),
                    new CentreCVT(2, "admin", "pass", "localhost", "cvtx2"),
                    new CentreCVT(3, "admin", "pass", "localhost", "cvtx3")
            ));
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            String encodedPassword = passwordEncoder.encode("password");

            userRepository.saveAll(List.of(
                    new User(
                            null,
                            "admin",
                            encodedPassword,
                            "Admin",
                            "User",
                            "أدمين",
                            "مستخدم",
                            LocalDate.now(),
                            LocalDate.now().plusYears(1),
                            true,
                            1, // ROLE_ADMIN
                            1  // ID_CENTRE 1
                    ),
                    new User(
                            null,
                            "inspector",
                            encodedPassword,
                            "Inspecteur",
                            "Test",
                            "مُفتش",
                            "تجربة",
                            LocalDate.now(),
                            LocalDate.now().plusYears(1),
                            true,
                            2, // ROLE_INSPECTOR
                            2  // ID_CENTRE 2
                    )
            ));

        }
    }
}
