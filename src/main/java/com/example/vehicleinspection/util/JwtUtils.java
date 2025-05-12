package com.example.vehicleinspection.util;

import com.example.vehicleinspection.model.User;
import com.example.vehicleinspection.model.enums.Role;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expiration;

    private  SecretKey key;

    private  JwtParser jwtParser;


    @PostConstruct
    public void init() {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("ID_CENTRE", user.getIdCentre())
                .claim("ROLE", Role.fromCode(user.getCodGrp())) // e.g., ROLE_ADMIN
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512) // Uses the correct key object
                .compact();
    }

    public String extractUsername(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

    public String extractIdCentreFromJwtToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody().get("ID_CENTRE").toString();
    }

    public String extractRoleFromJwtToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody().get("ROLE").toString();
    }

    public boolean validateJwtToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSecret() {
        return secret;
    }
}
