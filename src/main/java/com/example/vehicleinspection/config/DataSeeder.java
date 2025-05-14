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
                    new CentreCVT(10, "local1", "helmi1234", "localhost:1521", "XEPDB1"),
                    new CentreCVT(20, "local2", "helmi1234", "localhost:1521", "XEPDB1"),
                    new CentreCVT(30, "local3", "helmi1234", "localhost:1521", "XEPDB1")
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
                            "E",
                            "002", // INSPECTOR
                            20  // ID_CENTRE 2
                    )
            ));



        }
    }
    private void ensureTableExists() {
        Integer groupeCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ALL_TABLES WHERE TABLE_NAME = 'GROUPE' AND OWNER = USER", Integer.class);
        if (groupeCount == null || groupeCount == 0) {
            jdbcTemplate.execute("CREATE TABLE GROUPE ("
                    + "COD_GRP VARCHAR2(3) PRIMARY KEY, "
                    + "DESIGNATION VARCHAR2(100) NOT NULL UNIQUE"
                    + ")");
        }

        Integer utilisateursCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ALL_TABLES WHERE TABLE_NAME = 'UTILISATEURS' AND OWNER = USER", Integer.class);
        if (utilisateursCount == null || utilisateursCount == 0) {
            jdbcTemplate.execute(
                    "CREATE TABLE UTILISATEURS ("
                            + "ID_USER VARCHAR2(100) PRIMARY KEY, "
                            + "USERNAME VARCHAR2(100) UNIQUE NOT NULL, "
                            + "PASSE VARCHAR2(100) NOT NULL, "
                            + "PRENOM VARCHAR2(100), "
                            + "NOM VARCHAR2(100), "
                            + "PRENOMA VARCHAR2(100), "
                            + "NOMA VARCHAR2(100), "
                            + "DATE_DEB DATE, "
                            + "DATE_FIN DATE, "
                            + "ETAT VARCHAR2(1) NOT NULL, "
                            + "COD_GRP VARCHAR2(3) NOT NULL, "
                            + "ID_CENTRE NUMBER NOT NULL"
                            + ")");
        }

        Integer centreCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ALL_TABLES WHERE TABLE_NAME = 'CENTRE_CVT' AND OWNER = USER", Integer.class);
        if (centreCount == null || centreCount == 0) {
            jdbcTemplate.execute(
                    "CREATE TABLE CENTRE_CVT ("
                            + "ID_CENTRE NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, "
                            + "USERNAME VARCHAR2(50) NOT NULL, "
                            + "PASSWORD VARCHAR2(50) NOT NULL, "
                            + "MACHINE VARCHAR2(100), "
                            + "SID VARCHAR2(100)"
                            + ")");
        }
    }

}
