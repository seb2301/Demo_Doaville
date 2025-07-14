package com.doaville.controller;

import com.doaville.dto.ItemDoacaoCreateDTO;
import com.doaville.dto.ItemDoacaoDTO;
import com.doaville.exception.BusinessException;
import com.doaville.service.ItemDoacaoService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/itens-doacao")
public class ItemDoacaoController {

    @Autowired
    private ItemDoacaoService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ItemDoacaoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ItemDoacaoDTO> buscarPorId(@PathVariable Long id) {
        if (id == null || id <= 0)
            throw new BusinessException("Id inválido");
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemDoacaoDTO> criar(@RequestBody @Valid ItemDoacaoCreateDTO dto,
                                               @RequestHeader(value = "X-Custom-Header", required = false) String customHeader) {
        if (customHeader == null || customHeader.isBlank()) {
            throw new BusinessException("Header X-Custom-Header obrigatório");
        }
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ItemDoacaoDTO> editar(@PathVariable Long id, @RequestBody @Valid ItemDoacaoCreateDTO dto) {
        if (id == null || id <= 0)
            throw new BusinessException("Id inválido");
        return ResponseEntity.ok(service.editar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (id == null || id <= 0)
            throw new BusinessException("Id inválido");
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
