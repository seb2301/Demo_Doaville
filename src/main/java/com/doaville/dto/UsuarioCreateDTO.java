package com.doaville.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioCreateDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String nomeUsuario;
    @NotBlank
    private String senha;
    @NotBlank
    private String perfil; // ADMIN ou USER
}
