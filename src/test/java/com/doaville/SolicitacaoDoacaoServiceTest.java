package com.doaville;

import com.doaville.dto.SolicitacaoDoacaoCreateDTO;
import com.doaville.entity.ItemDoacao;
import com.doaville.entity.SolicitacaoDoacao;
import com.doaville.repository.ItemDoacaoRepository;
import com.doaville.repository.SolicitacaoDoacaoRepository;
import com.doaville.service.SolicitacaoDoacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolicitacaoDoacaoServiceTest {

    @Mock
    private SolicitacaoDoacaoRepository repository;
    @Mock
    private ItemDoacaoRepository itemRepository;

    @InjectMocks
    private SolicitacaoDoacaoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriar() {
        SolicitacaoDoacaoCreateDTO dto = new SolicitacaoDoacaoCreateDTO();
        dto.setIdItemDoacao(2L);
        dto.setQuantidade(10);
        dto.setEnderecoEntrega("Rua A");
        dto.setBairroEntrega("Bairro B");

        ItemDoacao item = new ItemDoacao();
        item.setId(2L);
        item.setNome("Teste");
        item.setAtivo(true);

        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        when(repository.save(any(SolicitacaoDoacao.class))).thenAnswer(i -> {
            SolicitacaoDoacao s = i.getArgument(0);
            s.setId(22L);
            return s;
        });

        var result = service.criar(dto);
        assertNotNull(result);
        assertEquals(2L, result.getIdItemDoacao());
    }

    @Test
    void testListarTodos() {
        SolicitacaoDoacao entity = new SolicitacaoDoacao();
        ItemDoacao item = new ItemDoacao();
        item.setId(1L);
        item.setNome("item1");
        entity.setId(1L);
        entity.setItemDoacao(item);
        when(repository.findAll()).thenReturn(List.of(entity));
        var lista = service.listarTodos();
        assertEquals(1, lista.size());
    }

    @Test
    void testBuscarPorId() {
        SolicitacaoDoacao entity = new SolicitacaoDoacao();
        ItemDoacao item = new ItemDoacao();
        item.setId(1L);
        item.setNome("item1");
        entity.setId(1L);
        entity.setItemDoacao(item);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        var dto = service.buscarPorId(1L);
        assertNotNull(dto);
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).deleteById(1L);
        service.excluir(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void testListarPorItem() {
        SolicitacaoDoacao entity = new SolicitacaoDoacao();
        ItemDoacao item = new ItemDoacao();
        item.setId(1L);
        item.setNome("item1");
        entity.setId(1L);
        entity.setItemDoacao(item);
        when(repository.findByItemDoacao_Id(1L)).thenReturn(List.of(entity));
        var lista = service.listarPorItem(1L);
        assertEquals(1, lista.size());
    }
}
