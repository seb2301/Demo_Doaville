package com.doaville.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_doacao", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemDoacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Boolean ativo;
}
