package com.doaville.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "nomeUsuario"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String nomeUsuario;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    private String perfil; // ADMIN ou USER
}
