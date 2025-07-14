package com.doaville.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String nomeUsuario;
    private String perfil;
}
