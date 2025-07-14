package com.doaville;

import com.doaville.controller.SolicitacaoDoacaoController;
import com.doaville.dto.SolicitacaoDoacaoCreateDTO;
import com.doaville.dto.SolicitacaoDoacaoDTO;
import com.doaville.service.SolicitacaoDoacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolicitacaoDoacaoControllerTest {

    @InjectMocks
    private SolicitacaoDoacaoController controller;

    @Mock
    private SolicitacaoDoacaoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriar() {
        var createDTO = new SolicitacaoDoacaoCreateDTO();
        var dto = mock(SolicitacaoDoacaoDTO.class);

        when(service.criar(createDTO)).thenReturn(dto);
        ResponseEntity<SolicitacaoDoacaoDTO> response = controller.criar(createDTO);

        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testListarTodos() {
        when(service.listarTodos()).thenReturn(List.of(new SolicitacaoDoacaoDTO()));
        List<SolicitacaoDoacaoDTO> resultado = controller.listarTodos();
        assertFalse(resultado.isEmpty());
    }

    @Test
    void testBuscarPorId() {
        var dto = new SolicitacaoDoacaoDTO();
        when(service.buscarPorId(1L)).thenReturn(dto);

        ResponseEntity<SolicitacaoDoacaoDTO> response = controller.buscarPorId(1L);
        assertEquals(dto, response.getBody());
    }

    @Test
    void testExcluir() {
        doNothing().when(service).excluir(1L);
        ResponseEntity<Void> response = controller.excluir(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(service).excluir(1L);
    }

    @Test
    void testListarPorItem() {
        when(service.listarPorItem(5L)).thenReturn(List.of(new SolicitacaoDoacaoDTO()));
        List<SolicitacaoDoacaoDTO> resultado = controller.listarPorItem(5L);
        assertFalse(resultado.isEmpty());
    }
}
