package com.doaville.service;

import com.doaville.dto.UsuarioCreateDTO;
import com.doaville.dto.UsuarioDTO;
import com.doaville.entity.Usuario;
import com.doaville.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioDTO criar(UsuarioCreateDTO dto) {
        if (repository.findByNomeUsuario(dto.getNomeUsuario()).isPresent()) {
            throw new RuntimeException("Nome de usuário já existe.");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setSenha(encoder.encode(dto.getSenha()));
        usuario.setPerfil(dto.getPerfil());
        repository.save(usuario);
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getNomeUsuario(), usuario.getPerfil());
    }

    public List<UsuarioDTO> listarTodos() {
        return repository.findAll().stream()
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getNomeUsuario(), usuario.getPerfil()))
                .collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getNomeUsuario(), usuario.getPerfil());
    }

    public UsuarioDTO editar(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setNome(dto.getNome());
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setSenha(encoder.encode(dto.getSenha()));
        usuario.setPerfil(dto.getPerfil());
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getNomeUsuario(), usuario.getPerfil());
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
