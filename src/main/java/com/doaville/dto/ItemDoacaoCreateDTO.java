package com.doaville.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemDoacaoCreateDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private Boolean ativo;
}
