package org.thej.foodorder.master.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.thej.foodorder.master.dao.Privilege;
import org.thej.foodorder.master.dao.Role;
import org.thej.foodorder.master.dao.User;
import org.thej.foodorder.master.dto.auth.LoginResponse;
import org.thej.foodorder.master.dto.auth.RegisterResponse;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("q+UCdvuvFiI+1iFmHynmyGOMlyZtZK9SuEdsNvqHheo=")
    private String SECRET;
    @Value("3600")
    private Long EXPIRATION;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Object extractRoles(String token) {
        return extractAllClaims(token).get("roles");
    }

    public Object extractPrivileges(String token) {
        return extractAllClaims(token).get("privileges");
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

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        if (EXPIRATION == null || SECRET == null) {
            throw new IllegalStateException("JWT configuration missing");
        }

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles());
        claims.put("privileges", user.getPrivileges());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
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
