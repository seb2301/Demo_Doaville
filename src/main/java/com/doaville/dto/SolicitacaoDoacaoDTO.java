package com.doaville.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SolicitacaoDoacaoDTO {
    private Long id;
    private Long idItemDoacao;
    private String nomeItem;
    private Integer quantidade;
    private LocalDateTime dataSolicitacao;
    private String enderecoEntrega;
    private String bairroEntrega;
}
