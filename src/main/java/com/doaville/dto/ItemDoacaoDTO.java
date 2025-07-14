package com.doaville.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder(toBuilder = true)
public class ItemDoacaoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
}

