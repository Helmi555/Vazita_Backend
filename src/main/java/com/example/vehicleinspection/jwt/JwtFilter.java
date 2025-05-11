package com.example.vehicleinspection.jwt;


import com.example.vehicleinspection.config.datasource.RoutingDataSourceContext;
import com.example.vehicleinspection.util.JwtUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = parseJwt(request);
        if (token != null && validateJwtToken(token)) {
            String idCentre = jwtUtils.extractIdCentreFromJwtToken(token);
            RoutingDataSourceContext.setDataSource("local_" + idCentre);
        }

        filterChain.doFilter(request, response);
        RoutingDataSourceContext.clear();
    }

    private String parseJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtUtils.getSecret().getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }



}