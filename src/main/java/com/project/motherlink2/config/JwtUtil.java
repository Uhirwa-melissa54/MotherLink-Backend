package com.project.motherlink2.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secret;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtUtil() {
        Dotenv dotenv = Dotenv.load();
        this.secret = dotenv.get("JWT_SECRET");
        this.accessTokenExpiration = Long.parseLong(dotenv.get("ACCESS_TOKEN_EXPIRATION"));
        this.refreshTokenExpiration = Long.parseLong(dotenv.get("REFRESH_TOKEN_EXPIRATION"));
    }

    // Generate Access Token
    public String generateAccessToken(String username, String district, String sector) {
        return Jwts.builder()
                .setSubject(username)                   // email or username
                .claim("district", district)            // add district claim
                .claim("sector", sector)                // add sector claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    // Generate Refresh Token
    public String generateRefreshToken(String email, String name,String district,String sector) {
        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("district", district)
                .claim("sector", sector)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    // Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }


    }

    // Extract username
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getName(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("name", String.class);
    }

    public String extractDistrict(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("district", String.class);
    }

    public String extractSector(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("sector", String.class);
    }

}
