package com.example.guitarshop_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Sử dụng một secret key cố định (chuỗi ngẫu nhiên dài hơn 64 ký tự cho chuẩn HS512)
    // Điều này giúp token không bị invalid mỗi khi restart Server
    private final String jwtSecretString = "GuitarShopSuperSecretKeyForJwtAuthenticationWhichNeedsToBeVeryLongToSatisfyHS512AlgorithmRequirements1234567890";
    private final SecretKey jwtSecret = Keys.hmacShaKeyFor(jwtSecretString.getBytes());

    // Thời gian sống của JWT (vd: 7 ngày = 604800000 ms)
    private final int jwtExpirationMs = 604800000;

    // Tạo token từ thông tin User
    public String generateToken(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    // Lấy email (username) từ token
    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("[JwtTokenProvider] Validating token: " + (authToken != null && authToken.length() > 10 ? authToken.substring(0, 10) + "..." : "invalid"));
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            System.out.println("[JwtTokenProvider] Token is VALID!");
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.err.println("[JwtTokenProvider] Invalid JWT signature or token format: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("[JwtTokenProvider] JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("[JwtTokenProvider] JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("[JwtTokenProvider] JWT claims string is empty: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[JwtTokenProvider] JWT validation encountered unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
