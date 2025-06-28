package org.thej.foodorder.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.thej.foodorder.auth.dto.LoginResponse;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.expiration}")
    private Long EXPIRATION;


    public String generateToken(LoginResponse loginResponse) {
        return createToken(new HashMap<>(), loginResponse);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.isBefore(Date.now());
    }

    private String createToken(Map<String, Object> claims, LoginResponse loginResponse) {
        if (EXPIRATION == null || SECRET == null) {
            throw new IllegalStateException("JWT configuration missing");
        }

        claims.put("userId", loginResponse.getUserId());
        claims.put("username", loginResponse.getUsername());
        claims.put("role", loginResponse.getRoles());
        claims.put("privileges", loginResponse.getPrivileges());

        return Jwts.builder()
                .claims(claims)
                .subject(loginResponse.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        } catch (IllegalArgumentException e) {

            log.error("Invalid JWT Secret: must be base64-encoded");
            throw new IllegalStateException("Invalid JWT secret format", e);
        }
    }
}
