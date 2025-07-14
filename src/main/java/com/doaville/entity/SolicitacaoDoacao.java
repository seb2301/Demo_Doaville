package com.doaville.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacao_doacao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SolicitacaoDoacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_item_doacao")
    private ItemDoacao itemDoacao;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column(nullable = false)
    private String enderecoEntrega;

    @Column(nullable = false)
    private String bairroEntrega;
}
