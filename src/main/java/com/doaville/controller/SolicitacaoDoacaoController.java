package com.doaville.controller;

import com.doaville.dto.SolicitacaoDoacaoCreateDTO;
import com.doaville.dto.SolicitacaoDoacaoDTO;
import com.doaville.service.SolicitacaoDoacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes-doacao")
@RequiredArgsConstructor
public class SolicitacaoDoacaoController {

    private final SolicitacaoDoacaoService service;

    @PostMapping
    public ResponseEntity<SolicitacaoDoacaoDTO> criar(@RequestBody @Valid SolicitacaoDoacaoCreateDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping
    public List<SolicitacaoDoacaoDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoDoacaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter/{itemId}")
    public List<SolicitacaoDoacaoDTO> listarPorItem(@PathVariable Long itemId) {
        return service.listarPorItem(itemId);
    }
}
