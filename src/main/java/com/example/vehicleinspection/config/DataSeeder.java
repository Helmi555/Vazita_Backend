package com.example.vehicleinspection.config;

import com.example.vehicleinspection.config.datasource.DataSourceManager;
import com.example.vehicleinspection.config.datasource.RoutingDataSourceContext;
import com.example.vehicleinspection.model.*;
import com.example.vehicleinspection.model.enums.Role;
import com.example.vehicleinspection.model.keys.DossierDefautId;
import com.example.vehicleinspection.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration

public class DataSeeder {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CentreCVTRepository centreCVTRepository;

    private final ChapitreRepository chapitreRepository;
    private final PointDefautRepository pointDefautRepository;
    private final AlterationRepository alterationRepository;
    private final DossierRepository dossierRepository;
    private final DossierDefautRepository dossierDefautRepository;
    private final MarqueVEHRepository marqueVEHRepository
;    private final JdbcTemplate jdbcTemplate;
    private final DataSourceManager dataSourceManager;

    public DataSeeder(
            GroupRepository groupRepository,
            UserRepository userRepository,
            CentreCVTRepository centreCVTRepository,
            ChapitreRepository chapitreRepository,
            PointDefautRepository pointDefautRepository,
            AlterationRepository alterationRepository,
            DossierRepository dossierRepository,
            DossierDefautRepository dossierDefautRepository, MarqueVEHRepository marqueVEHRepository,
            JdbcTemplate jdbcTemplate,
            DataSourceManager dataSourceManager
    ) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.centreCVTRepository = centreCVTRepository;
        this.chapitreRepository = chapitreRepository;
        this.pointDefautRepository = pointDefautRepository;
        this.alterationRepository = alterationRepository;
        this.dossierRepository = dossierRepository;
        this.dossierDefautRepository = dossierDefautRepository;
        this.marqueVEHRepository = marqueVEHRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSourceManager = dataSourceManager;
    }

    @Bean
    public CommandLineRunner seedDb() {
        return args -> {
            ensureCentralTablesExist();
            seedRoles();
            seedCentres();
            seedUsers();

            // --- LOCAL (CENTRE) SCHEMA SEEDS ---
            centreCVTRepository.findAll().forEach(centre -> {
                String centreId = String.valueOf(centre.getIdCentre());
                // switch to that centre's DataSource
                dataSourceManager.ensureDataSource(centreId);

                // create & seed the five tables in the local DB
                ensureLocalTablesExist();
                seedChapitres();
                seedPointsDefauts();
                seedAlterations();
                seedDossiers();
                seedDossierDefauts();
                seedMarqueVeh();
                RoutingDataSourceContext.clear();
            });

            // back to default (central) DataSource
            RoutingDataSourceContext.clear();
        };
    }

    //— CENTRAL: existing tables —
    private void ensureCentralTablesExist() {
        jdbcTemplate.execute("""
            DECLARE 
              v_count INTEGER;
            BEGIN
              SELECT COUNT(*) INTO v_count FROM ALL_TABLES WHERE TABLE_NAME = 'GROUPE' AND OWNER = USER;
              IF v_count = 0 THEN
                EXECUTE IMMEDIATE 'CREATE TABLE GROUPE (
                  COD_GRP VARCHAR2(3) PRIMARY KEY, 
                  DESIGNATION VARCHAR2(100) NOT NULL UNIQUE
                )';
              END IF;

              SELECT COUNT(*) INTO v_count FROM ALL_TABLES WHERE TABLE_NAME = 'UTILISATEURS' AND OWNER = USER;
              IF v_count = 0 THEN
                EXECUTE IMMEDIATE 'CREATE TABLE UTILISATEURS (
                  ID_USER VARCHAR2(100) PRIMARY KEY, 
                  PASSE VARCHAR2(100) NOT NULL, 
                  PRENOM VARCHAR2(100), 
                  NOM VARCHAR2(100), 
                  PRENOMA VARCHAR2(100), 
                  NOMA VARCHAR2(100), 
                  DATE_DEB DATE, 
                  DATE_FIN DATE, 
                  ETAT VARCHAR2(1) NOT NULL, 
                  COD_GRP VARCHAR2(3) NOT NULL, 
                  ID_CENTRE NUMBER NOT NULL
                )';
              END IF;

              SELECT COUNT(*) INTO v_count FROM ALL_TABLES WHERE TABLE_NAME = 'CENTRE_CVT' AND OWNER = USER;
              IF v_count = 0 THEN
                EXECUTE IMMEDIATE 'CREATE TABLE CENTRE_CVT (
                  ID_CENTRE NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, 
                  USERNAME VARCHAR2(50) NOT NULL, 
                  PASSWORD VARCHAR2(50) NOT NULL, 
                  MACHINE VARCHAR2(100), 
                  SID VARCHAR2(100)
                )';
              END IF;
              END;
            """);
    }

    private void seedRoles() {
        if (groupRepository.count() == 0) {
            groupRepository.saveAll(List.of(
                    new Group("001", Role.ADMIN),
                    new Group("002", Role.INSPECTEUR),
                    new Group("003", Role.ADJOINT)
            ));
        }
    }

    private void seedCentres() {
        if (centreCVTRepository.count() == 0) {
            centreCVTRepository.saveAll(List.of(
                    new CentreCVT(10, "local1", "helmi1234", "localhost:1521", "XEPDB1"),
                    new CentreCVT(20, "local2", "helmi1234", "localhost:1521", "XEPDB1")
            ));
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    new User("1111","nour123@","NOUR","MAAYOUFI","أدمين","مستخدم",
                            LocalDate.now(), LocalDate.now().plusYears(1),
                            "E","001",10),
                    new User("1112","mohammed123@","MOHAMMED","ORIL","مُفتش","تجربة",
                            LocalDate.now(), LocalDate.now().plusYears(1),
                            "E","002",20)
            ));
        }
    }

    //— LOCAL: create the five new tables if they don't exist —
    private void ensureLocalTablesExist() {
        // CHAPITRES
        jdbcTemplate.execute(
                "BEGIN " +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE CHAPITRES (" +
                        "    CODE_CHAPITRE NUMBER PRIMARY KEY, " +
                        "    LIBELLE_CHAPITRE VARCHAR2(100)" +
                        "  )'; " +
                        "EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;"
        );
        // POINTS_DEFAUTS
        jdbcTemplate.execute(
                "BEGIN " +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE POINTS_DEFAUTS (" +
                        "    CODE_POINT NUMBER PRIMARY KEY, " +
                        "    CODE_CHAPITRE NUMBER, " +
                        "    LIBELLE_POINT VARCHAR2(200)" +
                        "  )'; " +
                        "EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;"
        );
        // ALTERATIONS
        jdbcTemplate.execute(
                "BEGIN " +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE ALTERATIONS (" +
                        "    CODE_ALTERATION NUMBER PRIMARY KEY, " +
                        "    CODE_CHAPITRE NUMBER, " +
                        "    CODE_POINT NUMBER, " +
                        "    LIBELLE_ALTERATION VARCHAR2(200)" +
                        "  )'; " +
                        "EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;"
        );
        // MES_DOSSIERS
        jdbcTemplate.execute(
                "BEGIN " +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE MES_DOSSIERS (" +
                        "    N_DOSSIER NUMBER PRIMARY KEY, " +
                        "    NUM_CHASSIS VARCHAR2(100), " +
                        "    IMMATRICULATION VARCHAR2(100), " +
                        "    C_PISTE NUMBER, " +
                        "    DATE_HEURE_ENREGISTREMENT TIMESTAMP," +
                        "    CODE_MARQUE VARCHAR2(50)"+
                        "  )'; " +
                        "EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;"
        );
        // DOSSIER_DEFAUTS
        jdbcTemplate.execute(
                "BEGIN " +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE DOSSIER_DEFAUTS (" +
                        "    N_DOSSIER NUMBER(10,0), " +
                        "    NUM_CENTRE NUMBER(5,0) NOT NULL, " +
                        "    DAT_CTRL DATE NOT NULL, " +
                        "    DATE_HEURE_ENREGISTREMENT TIMESTAMP, " +
                        "    NUM_CHASSIS VARCHAR2(25), " +
                        "    CODE_DEFAUT VARCHAR2(10), " +
                        "    MAT_AGENT VARCHAR2(100), " +
                        "    PRIMARY KEY (N_DOSSIER, CODE_DEFAUT)," +
                        "    CODE_MARQUE VARCHAR2(50)"+
                        "  )'; " +
                        "EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;"
        );

        // MARQUE_VEH
        jdbcTemplate.execute(
                "BEGIN " +
                        "  EXECUTE IMMEDIATE 'CREATE TABLE MARQUE_VEH (" +
                        "    CD_MARQ VARCHAR2(50) PRIMARY KEY, " +
                        "    DESIGL VARCHAR2(25), " +
                        "    DESIGA VARCHAR2(25) " +
                        "  )'; " +
                        "EXCEPTION WHEN OTHERS THEN IF SQLCODE != -955 THEN RAISE; END IF; END;"

        );
    }

    private void seedChapitres() {
        if (chapitreRepository.count() == 0) {
            chapitreRepository.saveAll(List.of(
                    new Chapitre(0, "CONFORMITE CARTE GRISE"),
                    new Chapitre(1, "FEUX / POLLUTION"),
                    new Chapitre(2, "CHASSIS SUSPENS"),
                    new Chapitre(3, "CARROSSERIE"),
                    new Chapitre(4, "AVERTISSEUR SONORE ET BRUIT"),
                    new Chapitre(5, "ROUES/DIRECTION"),
                    new Chapitre(6, "MECANISME"),
                    new Chapitre(7, "MESURES"),
                    new Chapitre(8, "EQUIPEMENTS SPECIAUX"),
                    new Chapitre(9, "AUTRES")
            ));
        }
    }

    private void seedPointsDefauts() {
        if (pointDefautRepository.count() == 0) {
            pointDefautRepository.saveAll(List.of(
                    new PointDefaut(1, 0, "IDENTIFIANTS DU VEHICULE"),
                    new PointDefaut(2, 0, "PLAQUE DU CONSTRUCTEUR"),
                    new PointDefaut(3, 0, "IDENTIFIANTS DU VEHICULE"),
                    new PointDefaut(4, 0, "PLAQUE D'IMMATRICULATION"),
                    new PointDefaut(5, 1, "FEUX DE ROUTE"),
                    new PointDefaut(6, 1, "FEUX DE CROISEMENT"),
                    new PointDefaut(7, 1, "FEUX DE POSITION AVANT ET ARRIERE"),
                    new PointDefaut(8, 1, "FEUX DE CHANGEMENT DE DIRECTION"),
                    new PointDefaut(9, 1, "FEUX STOP")

            ));
        }
    }

    private void seedAlterations() {
        if (alterationRepository.count() == 0) {
            alterationRepository.saveAll(List.of(
                    new Alteration(1, 0, 3, "Transformation notable non autorisée"),
                    new Alteration(5, 0, 2, "Non conforme (couleur, dimension)"),
                    new Alteration(2, 0, 4, "Portant un N° d'immatriculation faux"),
                    new Alteration(3, 0, 4, "Ecriture Illisible"),
                    new Alteration(4, 1, 0, "Mauvaise fixation")
            ));
        }
    }

    private void seedDossiers() {
        if (dossierRepository.count() == 0) {
            dossierRepository.saveAll(List.of(
                    new Dossier(1665887, "YU15DMG4MT005344", "TU5883221",  0, LocalDateTime.of(2025,5,12,11,6,57),"45"),
                    new Dossier(1665823, "KL1TJ5CD2DB014893", "TU6622163",  4, LocalDateTime.of(2025,5,12,13,0),"46"),
                    new Dossier(1665824, "LSJA36E6XP2260406", "TU582239",   5, LocalDateTime.of(2025,5,12,13,10),"47")
            ));
        }
    }

    private void seedDossierDefauts() {
        if (dossierDefautRepository.count() == 0) {
            dossierDefautRepository.saveAll(List.of(
                    new DossierDefaut(new DossierDefautId(1665887,"051"), 10, LocalDate.of(2025,5,12),
                            LocalDateTime.now(), "YU15DMG4MT005344", "AG001","45"),
                    new DossierDefaut(new DossierDefautId(1665823,"052"), 20, LocalDate.of(2025,5,12),
                            LocalDateTime.now(), "KL1TJ5CD2DB014893",  "AG002","46"),
                    new DossierDefaut(new DossierDefautId(1665824,"012"), 30, LocalDate.of(2025,5,12),
                            LocalDateTime.now(), "LSJA36E6XP2260406","AG003","47"),
                    new DossierDefaut(new DossierDefautId(1665824,"043"), 30, LocalDate.of(2025,5,12),
                            LocalDateTime.now(), "LSJA36E6XP2260406","AG003","47")
            ));
        }
    }
    private void seedMarqueVeh(){
        if(marqueVEHRepository.count()==0){
            marqueVEHRepository.saveAll(List.of(
                    new MarqueVEH("47","toyota","arabic"),
                    new MarqueVEH("45","BMW","arabic"),
                    new MarqueVEH("46","audi","arabic2"),
                    new MarqueVEH("48","ToyotA","arabic"),
                    new MarqueVEH("49","mercedez","arabic"),
                    new MarqueVEH("50","Opel","arabic2")




                    ));
        }
    }
}
