package com.example.vehicleinspection.controller;

import com.example.vehicleinspection.repository.TestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private final DataSource dataSource;
    private final TestRepository  testRepository;

    public TestController(DataSource dataSource, TestRepository testRepository) {
        this.dataSource = dataSource;
        this.testRepository = testRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/db")
    public String testDBConnection() throws SQLException {
        //System.out.println("Testttttttttttttttttttttttt");
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            return "Connected to: " + metaData.getDatabaseProductName() +
                    " - " + metaData.getDatabaseProductVersion()+
                    " url "+metaData.getURL();

        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','INSPECTOR')")
    @GetMapping("/db-query")
    public ResponseEntity<?> testDBQuery() throws SQLException {
       // System.out.println("QUERyyyyyyyyyy");
        return ResponseEntity.ok(testRepository.findAll());
    }


    @GetMapping
    public String test() {
        return "test";
    }
}




