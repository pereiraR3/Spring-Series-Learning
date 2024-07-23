package com.exemple.curso.usuarios;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // faz com que entenda que o teste vá ser processado de acordo com application-test.properties
class UsuarioRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Should get Usuario successfully from DB")
    void findByLoginCase1() {

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("anthony", "121212");

        Usuario usuario = this.create(usuarioRequestDTO);

        UserDetails foundUser = this.usuarioRepository.findByLogin(usuario.getUsername()); // Corrigido: método chamado corretamente

        assertThat(foundUser.isEnabled()).isTrue();
    }

    @Test
    @DisplayName("Should not get Usuario from DB when user not exists")
    void findByLoginCase2() {

        String login = "henrique";

        UserDetails foundUser = this.usuarioRepository.findByLogin(login); // Corrigido: método chamado corretamente

        assertThat(foundUser).isNull();
    }

    public Usuario create(UsuarioRequestDTO body){

        String passwordEncrypted = new BCryptPasswordEncoder().encode(body.senha());

        Usuario usuario = new Usuario(body.login(), passwordEncrypted);

        this.entityManager.persist(usuario);

        return usuario;
    }
}
