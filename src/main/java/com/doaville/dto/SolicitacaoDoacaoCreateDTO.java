package com.doaville.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SolicitacaoDoacaoCreateDTO {
    @NotNull
    private Long idItemDoacao;

    @Min(1)
    private Integer quantidade;

    @NotBlank
    private String enderecoEntrega;

    @NotBlank
    private String bairroEntrega;
}
