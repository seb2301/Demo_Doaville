package com.doaville.service;

import com.doaville.dto.ItemDoacaoCreateDTO;
import com.doaville.dto.ItemDoacaoDTO;
import com.doaville.entity.ItemDoacao;
import com.doaville.repository.ItemDoacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemDoacaoService {

    private final ItemDoacaoRepository repository;

    public ItemDoacaoDTO criar(ItemDoacaoCreateDTO dto) {
        if (repository.findByNome(dto.getNome()).isPresent()) {
            throw new RuntimeException("Item de doação já existe.");
        }
        ItemDoacao item = new ItemDoacao();
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setAtivo(dto.getAtivo());
        repository.save(item);
        return new ItemDoacaoDTO(item.getId(), item.getNome(), item.getDescricao(), item.getAtivo());
    }

    public List<ItemDoacaoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(item -> new ItemDoacaoDTO(item.getId(), item.getNome(), item.getDescricao(), item.getAtivo()))
                .collect(Collectors.toList());
    }

    public ItemDoacaoDTO buscarPorId(Long id) {
        ItemDoacao item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item não encontrado"));
        return new ItemDoacaoDTO(item.getId(), item.getNome(), item.getDescricao(), item.getAtivo());
    }

    @Transactional
    public ItemDoacaoDTO editar(Long id, ItemDoacaoCreateDTO dto) {
        ItemDoacao item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item não encontrado"));
        item.setNome(dto.getNome());
        item.setDescricao(dto.getDescricao());
        item.setAtivo(dto.getAtivo());
        return new ItemDoacaoDTO(item.getId(), item.getNome(), item.getDescricao(), item.getAtivo());
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
