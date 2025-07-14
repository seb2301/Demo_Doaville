package com.doaville.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Lógica real de autenticação
        if ("admin".equals(request.getUsername()) && "admin".equals(request.getPassword())) {
            // Geração do token (aqui um valor fictício, troque pela geração real depois)
            String token = "token-fake-exemplo";

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciais inválidas");
            return ResponseEntity.status(401).body(error);
        }
    }

    // Classe auxiliar para deserializar o JSON da requisição
    public static class LoginRequest {
        private String username;
        private String password;

        // getters e setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
