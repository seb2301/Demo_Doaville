package com.doaville;

import com.doaville.dto.UsuarioCreateDTO;
import com.doaville.entity.Usuario;
import com.doaville.repository.UsuarioRepository;
import com.doaville.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriar() {
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setNome("teste");
        dto.setNomeUsuario("login");
        dto.setSenha("senha");
        dto.setPerfil("ADMIN");

        when(repository.findByNomeUsuario("login")).thenReturn(Optional.empty());
        when(encoder.encode("senha")).thenReturn("hashed");
        when(repository.save(any(Usuario.class))).thenAnswer(i -> {
            Usuario u = i.getArgument(0);
            u.setId(10L);
            return u;
        });

        var result = service.criar(dto);
        assertNotNull(result);
        assertEquals("login", result.getNomeUsuario());
    }

    @Test
    void testListarTodos() {
        Usuario entity = new Usuario();
        entity.setId(1L);
        entity.setNome("nome");
        entity.setNomeUsuario("user");
        entity.setPerfil("perfil");

        when(repository.findAll()).thenReturn(List.of(entity));
        var lista = service.listarTodos();
        assertEquals(1, lista.size());
    }

    @Test
    void testBuscarPorId() {
        Usuario entity = new Usuario();
        entity.setId(2L);
        entity.setNome("nome");
        entity.setNomeUsuario("user");
        entity.setPerfil("perfil");
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        var dto = service.buscarPorId(2L);
        assertNotNull(dto);
        assertEquals("user", dto.getNomeUsuario());
    }

    @Test
    void testEditar() {
        Usuario entity = new Usuario();
        entity.setId(3L);
        entity.setNome("a");
        entity.setNomeUsuario("b");
        entity.setPerfil("ADMIN");
        entity.setSenha("123");

        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setNome("novo");
        dto.setNomeUsuario("novoLogin");
        dto.setPerfil("USER");
        dto.setSenha("novaSenha");

        when(repository.findById(3L)).thenReturn(Optional.of(entity));
        when(encoder.encode("novaSenha")).thenReturn("hashNova");
        var res = service.editar(3L, dto);
        assertNotNull(res);
        assertEquals("novoLogin", res.getNomeUsuario());
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).deleteById(4L);
        service.excluir(4L);
        verify(repository).deleteById(4L);
    }
}
