package com.example.vehicleinspection.exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private DataSource dataSource;

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleEmailAlreadyExistsException(ElementNotFoundException ex) {
        return new ResponseEntity<>(Map.of("message",ex.getMessage()), HttpStatus.NOT_FOUND); // 404
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.UNAUTHORIZED); // 401
    }
    @ExceptionHandler(AccessMemberDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessMemberDeniedException(AccessMemberDeniedException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of("message", ex.getMessage()),
                HttpStatus.NOT_FOUND // 404
        );
    }
//    @ExceptionHandler(JpaSystemException.class)
//    public ResponseEntity<Map<String, Object>> handleJpaSystemException(JpaSystemException ex) {
//        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(MemberBannedException.class)
    public ResponseEntity<Map<String, Object>> handleMemberBannedException(MemberBannedException ex) {
        return new ResponseEntity<>(
          Map.of(
                  "message",ex.getMessage()
          )     ,
          HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS
        );
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String,Object>> handleFileSizeLimitExceeded(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(Map.of("message","File size exceeds the limit of 5MB"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getMessage();

        // Handle unique constraint violations
        if (message.contains("unique constraint") || message.contains("duplicate key")) {
            Pattern pattern = Pattern.compile("constraint \"(.*?)\"");
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                String constraintName = matcher.group(1);
                String columnName = getColumnNameFromConstraint(constraintName);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "error", "DUPLICATE_" + columnName.toUpperCase(),
                                "message", columnName + " already exists"
                        ));
            }
        }

        // Handle ban constraint separately (if not caught by JpaSystemException)
        if (message.contains("User already has an active ban")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "USER_ALREADY_BANNED",
                            "message", "User is already banned"
                    ));
        }

        // Generic database error
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "DATABASE_ERROR",
                        "message", "Data integrity violation"
                ));
    }






//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        String message = ex.getMostSpecificCause().getMessage();
//
//        Pattern pattern = Pattern.compile("constraint \"(.*?)\"");
//        Matcher matcher = pattern.matcher(message);
//
//        if (matcher.find()) {
//            String constraintName = matcher.group(1);
//
//            String columnName = getColumnNameFromConstraint(constraintName);
//
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(Map.of("message",columnName + " already exists. Please use a different one."));
//        }
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","Database error: " + message));
//    }
//


    private String getColumnNameFromConstraint(String constraintName) {
        String sql = """
            SELECT a.attname AS column_name
            FROM pg_constraint c
            JOIN pg_class t ON c.conrelid = t.oid
            JOIN pg_attribute a ON a.attrelid = t.oid AND a.attnum = ANY(c.conkey)
            WHERE c.conname = ?
        """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, constraintName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("column_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Field";
    }
}
