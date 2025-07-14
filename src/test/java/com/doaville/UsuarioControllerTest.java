package com.doaville;

import com.doaville.controller.UsuarioController;
import com.doaville.dto.UsuarioCreateDTO;
import com.doaville.dto.UsuarioDTO;
import com.doaville.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController controller;

    @Mock
    private UsuarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriar() {
        var createDTO = new UsuarioCreateDTO();
        var dto = new UsuarioDTO();

        when(service.criar(createDTO)).thenReturn(dto);
        ResponseEntity<UsuarioDTO> response = controller.criar(createDTO);

        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testListarTodos() {
        when(service.listarTodos()).thenReturn(List.of(new UsuarioDTO()));
        List<UsuarioDTO> resultado = controller.listarTodos();
        assertFalse(resultado.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        var dto = new UsuarioDTO();
        when(service.buscarPorId(1L)).thenReturn(dto);

        ResponseEntity<UsuarioDTO> response = controller.buscarPorId(1L);
        assertEquals(dto, response.getBody());
    }

    @Test
    void testEditar() {
        var createDTO = new UsuarioCreateDTO();
        var dto = new UsuarioDTO();

        when(service.editar(eq(1L), eq(createDTO))).thenReturn(dto);
        ResponseEntity<UsuarioDTO> response = controller.editar(1L, createDTO);

        assertEquals(dto, response.getBody());
    }

    @Test
    void testExcluir() {
        doNothing().when(service).excluir(1L);
        ResponseEntity<Void> response = controller.excluir(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(service).excluir(1L);
    }
}
