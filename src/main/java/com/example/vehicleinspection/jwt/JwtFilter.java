package com.example.vehicleinspection.jwt;


import com.example.vehicleinspection.config.datasource.RoutingDataSourceContext;
import com.example.vehicleinspection.util.JwtUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final JwtBlacklistService jwtBlacklistService;
    private final static Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public JwtFilter(JwtUtils jwtUtils, JwtBlacklistService jwtBlacklistService) {
        this.jwtUtils = jwtUtils;
        this.jwtBlacklistService = jwtBlacklistService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String path=request.getRequestURI();
        if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = parseJwt(request);
        logger.info(">>> Incoming path={} parsedToken={}", request.getRequestURI(), token);

        if (token != null) {
            if (jwtBlacklistService.isTokenBlacklisted(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                return;
            }

            try {
                jwtUtils.validateJwtToken(token);
                String username = jwtUtils.extractUsername(token);
                String role = jwtUtils.extractRoleFromJwtToken(token);
                String centreId = jwtUtils.extractIdCentreFromJwtToken(token);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);

                RoutingDataSourceContext.setDataSource(centreId);
            } catch (JwtException ex) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return;
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            RoutingDataSourceContext.clear();
        }
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