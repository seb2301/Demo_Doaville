package com.doaville.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private String username = "admin";
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Pega a mesma chave do serviço para garantir a assinatura correta
        secretKey = Keys.hmacShaKeyFor(
                "f56c9732c0f744e8abf8f67c28c731f502c44b80a8ee4998932f4c6f0e2bda63babeaa0e7a2e68d1b6b183925dc2b8e5"
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    private String generateValidToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60_000)) // 1 minuto válido
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateExpiredToken(String username) {
        // Expirou há 1 segundo
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 120_000))
                .setExpiration(new Date(System.currentTimeMillis() - 60_000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateTokenWithWrongSignature(String username) {
        SecretKey wrongKey = Keys.hmacShaKeyFor(
                "umasecretkeytotalmentediferenteedemaiostamanho".repeat(2).getBytes(StandardCharsets.UTF_8)
        );
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(wrongKey, SignatureAlgorithm.HS512)
                .compact();
    }

    @Test
    void testGenerateToken() {
        User user = new User(username, "123", Collections.emptyList());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        String token = jwtService.generateToken(auth);

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token));
        assertEquals(username, jwtService.getUsername(token));
    }

    @Test
    void testIsTokenValid_comTokenValido() {
        String token = generateValidToken(username);
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void testIsTokenValid_comTokenExpirado() {
        String token = generateExpiredToken(username);
        assertFalse(jwtService.isTokenValid(token));
    }

    @Test
    void testIsTokenValid_comTokenMalFormado() {
        assertFalse(jwtService.isTokenValid("token-invalido"));
    }

    @Test
    void testIsTokenValid_comTokenFake() {
        assertTrue(jwtService.isTokenValid("token-fake-exemplo"));
    }

    @Test
    void testIsTokenValid_comTokenAssinaturaErrada() {
        String token = generateTokenWithWrongSignature(username);
        assertFalse(jwtService.isTokenValid(token));
    }

    @Test
    void testGetClaims_comTokenFake() {
        Claims claims = jwtService.getClaims("token-fake-exemplo");
        assertNotNull(claims);
        assertEquals("admin", claims.getSubject());
    }

    @Test
    void testGetClaims_comTokenValido() {
        String token = generateValidToken(username);
        Claims claims = jwtService.getClaims(token);
        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
    }

    @Test
    void testGetClaims_comTokenExpirado() {
        String token = generateExpiredToken(username);
        Claims claims = jwtService.getClaims(token);
        assertNull(claims);
    }

    @Test
    void testGetClaims_comTokenMalFormado() {
        Claims claims = jwtService.getClaims("token-invalido");
        assertNull(claims);
    }

    @Test
    void testGetClaims_comTokenAssinaturaErrada() {
        String token = generateTokenWithWrongSignature(username);
        Claims claims = jwtService.getClaims(token);
        assertNull(claims);
    }

    @Test
    void testGetUsername_comTokenValido() {
        String token = generateValidToken(username);
        assertEquals(username, jwtService.getUsername(token));
    }

    @Test
    void testGetUsername_comTokenFake() {
        assertEquals("admin", jwtService.getUsername("token-fake-exemplo"));
    }

    @Test
    void testGetUsername_comTokenMalFormado() {
        assertNull(jwtService.getUsername("token-invalido"));
    }

    @Test
    void testGetAuthentication_comTokenValido() {
        String token = generateValidToken(username);
        var auth = jwtService.getAuthentication(token);
        assertNotNull(auth);
        assertEquals(username, auth.getName());
        assertTrue(auth.getAuthorities().isEmpty());
    }

    @Test
    void testGetAuthentication_comTokenFake() {
        var auth = jwtService.getAuthentication("token-fake-exemplo");
        assertNotNull(auth);
        assertEquals("admin", auth.getName());
        var authList = auth.getAuthorities();
        assertTrue(authList.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(authList.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testGetAuthentication_comTokenMalFormado() {
        var auth = jwtService.getAuthentication("token-invalido");
        assertNull(auth);
    }

    @Test
    void testGetAuthentication_comTokenExpirado() {
        String token = generateExpiredToken(username);
        var auth = jwtService.getAuthentication(token);
        assertNull(auth);
    }

    @Test
    void testGetAuthentication_comTokenAssinaturaErrada() {
        String token = generateTokenWithWrongSignature(username);
        var auth = jwtService.getAuthentication(token);
        assertNull(auth);
    }

}
