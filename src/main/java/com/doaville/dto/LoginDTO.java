package com.doaville.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoginDTO {
    @NotBlank
    private String nomeUsuario;
    @NotBlank
    private String senha;
}
