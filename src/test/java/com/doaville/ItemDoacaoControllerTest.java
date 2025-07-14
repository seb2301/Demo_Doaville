package com.doaville;

import com.doaville.controller.ItemDoacaoController;
import com.doaville.dto.ItemDoacaoCreateDTO;
import com.doaville.dto.ItemDoacaoDTO;
import com.doaville.exception.BusinessException;
import com.doaville.service.ItemDoacaoService;
import com.doaville.security.SecurityConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ItemDoacaoController.class)
@Import(SecurityConfig.class)
class ItemDoacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemDoacaoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ItemDoacaoDTO validDTO;
    private ItemDoacaoCreateDTO validCreateDTO;

    @BeforeEach
    void setup() {
        validCreateDTO = new ItemDoacaoCreateDTO("Cesta Básica", "Alimentos não perecíveis", true);
        validDTO = new ItemDoacaoDTO(1L, "Cesta Básica", "Alimentos não perecíveis", true);
    }

    @Test
    @WithMockUser
    void listarTodosVazio() throws Exception {
        when(service.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/itens-doacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    @WithMockUser
    void listarTodosComItens() throws Exception {
        List<ItemDoacaoDTO> lista = Arrays.asList(validDTO);
        when(service.listarTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/itens-doacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is("Cesta Básica")));
    }

    @Test
    @WithMockUser
    void buscarPorIdExistente() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(validDTO);

        mockMvc.perform(get("/api/itens-doacao/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser
    void buscarPorIdInexistente() throws Exception {
        when(service.buscarPorId(999L)).thenThrow(new BusinessException("Não encontrado"));

        mockMvc.perform(get("/api/itens-doacao/999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemComSucesso() throws Exception {
        when(service.criar(any())).thenReturn(validDTO);

        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemCamposInvalidos() throws Exception {
        ItemDoacaoCreateDTO dtoInvalido = new ItemDoacaoCreateDTO("", "", null);

        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemBusinessException() throws Exception {
        when(service.criar(any())).thenThrow(new BusinessException("Erro de negócio"));

        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void erroInternoAoCriarItem() throws Exception {
        when(service.criar(any())).thenThrow(new RuntimeException("Falha interna"));

        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void editarItemComSucesso() throws Exception {
        when(service.editar(eq(1L), any())).thenReturn(validDTO);

        mockMvc.perform(put("/api/itens-doacao/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void editarItemCamposInvalidos() throws Exception {
        ItemDoacaoCreateDTO dtoInvalido = new ItemDoacaoCreateDTO(null, "", null);

        mockMvc.perform(put("/api/itens-doacao/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void editarItemInexistente() throws Exception {
        when(service.editar(eq(999L), any())).thenThrow(new BusinessException("Não encontrado"));

        mockMvc.perform(put("/api/itens-doacao/999")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void excluirItemComSucesso() throws Exception {
        doNothing().when(service).excluir(1L);

        mockMvc.perform(delete("/api/itens-doacao/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void excluirItemInexistente() throws Exception {
        doThrow(new BusinessException("Não encontrado")).when(service).excluir(999L);

        mockMvc.perform(delete("/api/itens-doacao/999")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void erroInternoAoExcluirItem() throws Exception {
        doThrow(new RuntimeException("Falha interna")).when(service).excluir(2L);

        mockMvc.perform(delete("/api/itens-doacao/2")
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void erroInternoAoBuscarPorId() throws Exception {
        when(service.buscarPorId(2L)).thenThrow(new RuntimeException("Falha interna"));

        mockMvc.perform(get("/api/itens-doacao/2"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void erroInternoAoEditarItem() throws Exception {
        when(service.editar(eq(2L), any())).thenThrow(new RuntimeException("Falha interna"));

        mockMvc.perform(put("/api/itens-doacao/2")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void acessoNegadoSemUsuario() throws Exception {
        mockMvc.perform(get("/api/itens-doacao"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemContentTypeErrado() throws Exception {
        when(service.criar(any())).thenReturn(validDTO);

        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.TEXT_PLAIN)
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @WithMockUser
    void buscarPorIdNegativo() throws Exception {
        mockMvc.perform(get("/api/itens-doacao/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void buscarPorIdString() throws Exception {
        mockMvc.perform(get("/api/itens-doacao/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void editarItemIdZero() throws Exception {
        mockMvc.perform(put("/api/itens-doacao/0")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void excluirItemIdNegativo() throws Exception {
        mockMvc.perform(delete("/api/itens-doacao/-5")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemHeaderErrado() throws Exception {
        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.TEXT_XML)
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemSemContentType() throws Exception {
        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @WithMockUser(username = "usuario", roles = "VISITANTE")
    void excluirItemSemPermissao() throws Exception {
        mockMvc.perform(delete("/api/itens-doacao/1")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemFaltandoHeaderCustom() throws Exception {
        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarItemComHeaderCustom() throws Exception {
        when(service.criar(any())).thenReturn(validDTO);

        mockMvc.perform(post("/api/itens-doacao")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Custom-Header", "123")
                        .content(objectMapper.writeValueAsString(validCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }
}
