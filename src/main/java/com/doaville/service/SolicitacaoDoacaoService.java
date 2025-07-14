package com.doaville.service;

import com.doaville.dto.SolicitacaoDoacaoCreateDTO;
import com.doaville.dto.SolicitacaoDoacaoDTO;
import com.doaville.entity.SolicitacaoDoacao;
import com.doaville.repository.ItemDoacaoRepository;
import com.doaville.repository.SolicitacaoDoacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitacaoDoacaoService {

    private final SolicitacaoDoacaoRepository repository;
    private final ItemDoacaoRepository itemRepository;

    public SolicitacaoDoacaoDTO criar(SolicitacaoDoacaoCreateDTO dto) {
        var item = itemRepository.findById(dto.getIdItemDoacao()).orElseThrow(() -> new RuntimeException("Item de doação não encontrado"));
        if (!item.getAtivo()) {
            throw new RuntimeException("Item de doação está inativo.");
        }
        SolicitacaoDoacao solicitacao = new SolicitacaoDoacao();
        solicitacao.setItemDoacao(item);
        solicitacao.setQuantidade(dto.getQuantidade());
        solicitacao.setEnderecoEntrega(dto.getEnderecoEntrega());
        solicitacao.setBairroEntrega(dto.getBairroEntrega());
        solicitacao.setDataSolicitacao(java.time.LocalDateTime.now());
        repository.save(solicitacao);
        // Aqui está a chamada corrigida:
        return new SolicitacaoDoacaoDTO(
                solicitacao.getId(),
                solicitacao.getItemDoacao().getId(),    // idItemDoacao
                solicitacao.getItemDoacao().getNome(),  // nomeItem
                solicitacao.getQuantidade(),
                solicitacao.getDataSolicitacao(),
                solicitacao.getEnderecoEntrega(),
                solicitacao.getBairroEntrega()
        );
    }

    public List<SolicitacaoDoacaoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(solicitacao -> new SolicitacaoDoacaoDTO(
                        solicitacao.getId(),
                        solicitacao.getItemDoacao().getId(),    // idItemDoacao
                        solicitacao.getItemDoacao().getNome(),  // nomeItem
                        solicitacao.getQuantidade(),
                        solicitacao.getDataSolicitacao(),
                        solicitacao.getEnderecoEntrega(),
                        solicitacao.getBairroEntrega()
                ))
                .collect(Collectors.toList());
    }

    public List<SolicitacaoDoacaoDTO> listarPorItem(Long itemId) {
        return repository.findByItemDoacao_Id(itemId).stream()
                .map(solicitacao -> new SolicitacaoDoacaoDTO(
                        solicitacao.getId(),
                        solicitacao.getItemDoacao().getId(),    // idItemDoacao
                        solicitacao.getItemDoacao().getNome(),  // nomeItem
                        solicitacao.getQuantidade(),
                        solicitacao.getDataSolicitacao(),
                        solicitacao.getEnderecoEntrega(),
                        solicitacao.getBairroEntrega()
                ))
                .collect(Collectors.toList());
    }

    public SolicitacaoDoacaoDTO buscarPorId(Long id) {
        SolicitacaoDoacao solicitacao = repository.findById(id).orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        return new SolicitacaoDoacaoDTO(
                solicitacao.getId(),
                solicitacao.getItemDoacao().getId(),    // idItemDoacao
                solicitacao.getItemDoacao().getNome(),  // nomeItem
                solicitacao.getQuantidade(),
                solicitacao.getDataSolicitacao(),
                solicitacao.getEnderecoEntrega(),
                solicitacao.getBairroEntrega()
        );
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
