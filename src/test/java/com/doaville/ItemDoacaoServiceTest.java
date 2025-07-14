package com.doaville.service;

import com.doaville.dto.ItemDoacaoCreateDTO;
import com.doaville.entity.ItemDoacao;
import com.doaville.repository.ItemDoacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemDoacaoServiceTest {

    private ItemDoacaoRepository repository;
    private ItemDoacaoService service;

    @BeforeEach
    void setUp() {
        repository = mock(ItemDoacaoRepository.class);
        service = new ItemDoacaoService(repository);
    }

    @Test
    void criar_deveSalvarNovoItem() {
        ItemDoacaoCreateDTO dto = new ItemDoacaoCreateDTO("Água", "Garrafa de água", true);
        when(repository.findByNome(dto.getNome())).thenReturn(Optional.empty());

        ItemDoacao salvo = new ItemDoacao();
        salvo.setId(1L);
        salvo.setNome(dto.getNome());
        salvo.setDescricao(dto.getDescricao());
        salvo.setAtivo(dto.getAtivo());

        when(repository.save(any(ItemDoacao.class))).thenReturn(salvo);

        var result = service.criar(dto);

        assertNotNull(result);
        assertEquals(dto.getNome(), result.getNome());
    }

    @Test
    void criar_deveLancarExcecaoSeNomeExistir() {
        ItemDoacaoCreateDTO dto = new ItemDoacaoCreateDTO("Água", "desc", true);
        when(repository.findByNome(dto.getNome())).thenReturn(Optional.of(new ItemDoacao()));

        assertThrows(RuntimeException.class, () -> service.criar(dto));
    }

    @Test
    void listarTodos_deveRetornarLista() {
        ItemDoacao item = new ItemDoacao();
        item.setId(1L); item.setNome("Água"); item.setDescricao("desc"); item.setAtivo(true);
        when(repository.findAll()).thenReturn(List.of(item));

        var result = service.listarTodos();

        assertEquals(1, result.size());
        assertEquals("Água", result.get(0).getNome());
    }

    @Test
    void buscarPorId_deveRetornarItem() {
        ItemDoacao item = new ItemDoacao();
        item.setId(1L); item.setNome("Água"); item.setDescricao("desc"); item.setAtivo(true);
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        var result = service.buscarPorId(1L);

        assertEquals("Água", result.getNome());
    }

    @Test
    void buscarPorId_deveLancarExcecaoSeNaoEncontrado() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.buscarPorId(2L));
    }

    @Test
    void editar_deveAtualizarItem() {
        ItemDoacao item = new ItemDoacao();
        item.setId(1L); item.setNome("Velho"); item.setDescricao("desc"); item.setAtivo(true);
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        ItemDoacaoCreateDTO dto = new ItemDoacaoCreateDTO("Novo", "descNova", false);

        var result = service.editar(1L, dto);

        assertEquals("Novo", result.getNome());
        assertEquals("descNova", result.getDescricao());
        assertFalse(result.getAtivo());
    }

    @Test
    void editar_deveLancarExcecaoSeNaoEncontrado() {
        when(repository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.editar(10L, new ItemDoacaoCreateDTO("x","x",true)));
    }

    @Test
    void excluir_deveChamarDeleteById() {
        service.excluir(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
