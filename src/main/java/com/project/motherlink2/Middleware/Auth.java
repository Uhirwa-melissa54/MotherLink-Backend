package com.project.motherlink2.Middleware;

import com.project.motherlink2.config.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class Auth extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip authentication for public endpoints AND Swagger
        if (path.equals("/api/admins/login")
                || path.equals("/api/admins/create")
                || path.equals("/mobile/healthworkers/register")
                || path.equals("/mobile/healthworkers/login")
                || path.equals("/api/organizations/create")
                || path.equals("/api/ambulances/create")
                || path.equals("/api/token")
                || path.startsWith("/swagger-ui")      // Swagger UI page
                || path.startsWith("/v3/api-docs")     // OpenAPI JSON
                || path.startsWith("/swagger-ui.html")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
