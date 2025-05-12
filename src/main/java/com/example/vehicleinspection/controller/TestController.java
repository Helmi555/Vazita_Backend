package com.example.vehicleinspection.controller;

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

    public TestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/db")
    public String testDBConnection() throws SQLException {
        System.out.println("Testttttttttttttttttttttttt");
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            return "Connected to: " + metaData.getDatabaseProductName() +
                    " - " + metaData.getDatabaseProductVersion();
        }
    }


    @GetMapping
    public String test() {
        return "test";
    }
}




