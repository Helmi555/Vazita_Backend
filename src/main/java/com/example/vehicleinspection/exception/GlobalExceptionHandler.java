package com.example.vehicleinspection.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private DataSource dataSource;
    private final static Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(MemberBannedException.class)
    public ResponseEntity<Map<String, Object>> handleMemberBannedException(MemberBannedException ex) {
        return new ResponseEntity<>(
          Map.of(
                  "message",ex.getMessage()
          )     ,
          HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS
        );
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<Map<String, String>> handleDbErrors(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Database error");
        error.put("error", "A database issue occurred.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : "global";
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
