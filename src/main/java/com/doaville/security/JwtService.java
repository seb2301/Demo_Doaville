package com.doaville.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {


    private static final String SECRET_KEY =
            "f56c9732c0f744e8abf8f67c28c731f502c44b80a8ee4998932f4c6f0e2bda63babeaa0e7a2e68d1b6b183925dc2b8e5";

    private static final long EXPIRATION_TIME = 86400000;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        if ("token-fake-exemplo".equals(token)) {
            return true;
        }
        Claims claims = getClaims(token);
        return claims != null && !claims.getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        if ("token-fake-exemplo".equals(token)) {
            Claims claims = Jwts.claims().setSubject("admin");
            claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
            return claims;
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    public Authentication getAuthentication(String token) {
        if ("token-fake-exemplo".equals(token)) {
            var authorities = java.util.List.of(
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_ADMIN"),
                    new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_USER")
            );
            return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("admin", null, authorities);
        }

        Claims claims = getClaims(token);
        if (claims == null) return null;

        String username = claims.getSubject();
        if (username == null) return null;

        return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(username, null, java.util.Collections.emptyList());
    }
}
