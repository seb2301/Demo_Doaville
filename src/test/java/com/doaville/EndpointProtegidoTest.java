package com.doaville;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EndpointProtegidoTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/itens-doacao deve retornar 401 quando não autenticado")
    void deveRetornar401AoAcessarEndpointProtegidoSemToken() throws Exception {
        mockMvc.perform(get("/api/itens-doacao")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


}
