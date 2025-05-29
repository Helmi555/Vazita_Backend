package com.example.vehicleinspection.jwt;


//import com.example.vehicleinspection.config.datasource.RoutingDataSourceContext;
import com.example.vehicleinspection.config.datasource.DataSourceManager;
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
import java.util.List;


@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final JwtBlacklistService jwtBlacklistService;
    private final DataSourceManager dataSourceManager;
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private static final List<String> allowedPaths = List.of("/api/v1/auth/login");
    private static final List<String> allowedCentralPaths = List.of("/api/v1/users","/api/v1/users/me");

    public JwtFilter(JwtUtils jwtUtils,
                     JwtBlacklistService jwtBlacklistService,
                     DataSourceManager dataSourceManager) {
        this.jwtUtils = jwtUtils;
        this.jwtBlacklistService = jwtBlacklistService;
        this.dataSourceManager = dataSourceManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String token = parseJwt(request);

        logger.info("Path is {} and method {} and token is {}", path, request.getMethod(),token);
        if (allowedPaths.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (token != null) {
            if (jwtBlacklistService.isTokenBlacklisted(token)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT token is blacklisted");
                return;
            }


            try {
                jwtUtils.validateJwtToken(token);

                String username = jwtUtils.extractIdUser(token);
                String role = jwtUtils.extractRoleFromJwtToken(token);
                Integer centreId = jwtUtils.extractIdCentreFromJwtToken(token);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);
                if (allowedCentralPaths.contains(path) || path.startsWith("/api/v1/users/")) {
                    logger.info("Hii updated user {}",path.startsWith("/api/v1/users/"));
                    filterChain.doFilter(request, response);
                    return;
                }
                dataSourceManager.ensureDataSource(centreId.toString());

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
}