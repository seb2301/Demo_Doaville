package com.doaville.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Simulação de banco de dados
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("$2a$10$KH2P.SrR8h2wz5VjABsHTOf/VwzPTdeMnAz0p60s6qlTwEuCOcbNe")
                    .roles("ADMIN")
                    .build();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
