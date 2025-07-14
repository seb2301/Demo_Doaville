package com.doaville.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TokenDTO {
    private String token;
    private String tipo = "Bearer";
}
