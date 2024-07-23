package com.exemple.curso.services;

import com.exemple.curso.usuarios.Usuario;
import com.exemple.curso.usuarios.UsuarioRepository;
import com.exemple.curso.usuarios.UsuarioRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find user by id successfully")
    void findByIdCase1() {
        Usuario usuario = new Usuario(1L, "testUser", "password");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario foundUser = usuarioService.findById(1L);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1L);
        assertThat(foundUser.getLogin()).isEqualTo("testUser");
    }

    @Test
    @DisplayName("Should throw exception when user not found by id")
    void findByIdCase2() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> usuarioService.findById(1L));
    }

    @Test
    @DisplayName("Should create user successfully")
    void create() {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("testUser", "password");
        Usuario usuario = new Usuario("testUser", new BCryptPasswordEncoder().encode("password"));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario createdUser = usuarioService.create(usuarioRequestDTO);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getLogin()).isEqualTo("testUser");
        assertThat(new BCryptPasswordEncoder().matches("password", createdUser.getSenha())).isTrue();
    }
}
