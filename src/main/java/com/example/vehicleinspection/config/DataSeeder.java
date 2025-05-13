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
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Configuration
public class DataSeeder {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CentreCVTRepository centreCVTRepository;
    private  final JdbcTemplate jdbcTemplate;

    public DataSeeder(GroupRepository groupRepository, UserRepository userRepository, CentreCVTRepository centreCVTRepository , JdbcTemplate jdbcTemplate) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.centreCVTRepository = centreCVTRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public CommandLineRunner seedDb() {
        return args -> {
            ensureTableExists();
            seedRoles();
            seedCentres();
            seedUsers();
        };
    }

    private void seedRoles() {
        if (groupRepository.count() == 0) {
            groupRepository.saveAll(List.of(
                    new Group("001", Role.ADMIN),
                    new Group("002", Role.INSPECTOR),
                    new Group("003", Role.ADJOINT)
            ));
        }
    }

    private void seedCentres() {
        if (centreCVTRepository.count() == 0) {
            centreCVTRepository.saveAll(List.of(
                    new CentreCVT(10, "admin", "pass", "localhost", "cvtx1"),
                    new CentreCVT(20, "admin", "pass", "localhost", "cvtx2"),
                    new CentreCVT(30, "admin", "pass", "localhost", "cvtx3")
            ));
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {

            userRepository.saveAll(List.of(
                    new User(
                            "1111",
                            "admin",
                            "nour123@",
                            "NOUR",
                            "MAAYOUFI",
                            "أدمين",
                            "مستخدم",
                            LocalDate.now(),
                            LocalDate.now().plusYears(1),
                            "E",
                            "001", // ADMIN
                            10  // ID_CENTRE 1
                    ),
                    new User(
                            "1112",
                            "inspector",
                            "mohammed123@",
                            "MOHAMMED",
                            "ORIL",
                            "مُفتش",
                            "تجربة",
                            LocalDate.now(),
                            LocalDate.now().plusYears(1),
                            "A",
                            "002", // INSPECTOR
                            20  // ID_CENTRE 2
                    )
            ));



        }
    }
    private void ensureTableExists() {
        Integer groupeCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'GROUPE'",
                Integer.class
        );
        if (groupeCount == null || groupeCount == 0) {
            jdbcTemplate.execute("CREATE TABLE GROUPE ("
                    + "COD_GRP VARCHAR(3) PRIMARY KEY, "
                    + "DESIGNATION VARCHAR(100) NOT NULL UNIQUE"
                    + ");");
        }

        Integer utilisateursCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'UTILISATEURS'",
                Integer.class
        );
        if (utilisateursCount == null || utilisateursCount == 0) {
            jdbcTemplate.execute(
                    "CREATE TABLE UTILISATEURS ("
                            + "ID_USER VARCHAR(100) PRIMARY KEY, "
                            + "USERNAME VARCHAR(100) UNIQUE NOT NULL, "
                            + "PASSE VARCHAR(100) NOT NULL, "
                            + "PRENOM VARCHAR(100), "
                            + "NOM VARCHAR(100), "
                            + "PRENOMA VARCHAR(100), "
                            + "NOMA VARCHAR(100), "
                            + "DATE_DEB DATE, "
                            + "DATE_FIN DATE, "
                            + "ETAT CHAR(1) NOT NULL, "
                            + "COD_GRP VARCHAR(3) NOT NULL, "
                            + "ID_CENTRE INT NOT NULL"
                            + ");"
            );
        }

        Integer centreCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'CENTRE_CVT'",
                Integer.class
        );
        if (centreCount == null || centreCount == 0) {
            jdbcTemplate.execute(
                    "CREATE TABLE CENTRE_CVT ("
                            + "ID_CENTRE INT PRIMARY KEY, "
                            + "USERNAME VARCHAR(50) NOT NULL, "
                            + "PASSWORD VARCHAR(50) NOT NULL, "
                            + "MACHINE VARCHAR(100), "
                            + "SID VARCHAR(100)"
                            + ");"
            );
        }
    }

}
