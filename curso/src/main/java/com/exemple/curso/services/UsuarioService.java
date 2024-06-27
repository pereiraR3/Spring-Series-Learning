package com.exemple.curso.services;

import com.exemple.curso.usuarios.Usuario;
import com.exemple.curso.usuarios.UsuarioRepository;
import com.exemple.curso.usuarios.UsuarioRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario findById(Long id) {

        return this.usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

    }

    public Usuario create(UsuarioRequestDTO body) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.senha());

        Usuario usuario = new Usuario(body.login(), encryptedPassword);

        this.usuarioRepository.save(usuario);

        return usuario;
    }


}
