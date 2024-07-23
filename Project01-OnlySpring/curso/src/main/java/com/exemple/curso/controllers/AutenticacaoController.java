package com.exemple.curso.controllers;

import com.exemple.curso.infra.DadosTokenJWT;
import com.exemple.curso.infra.TokenService;
import com.exemple.curso.services.UsuarioService;
import com.exemple.curso.usuarios.DadosAutenticacao;
import com.exemple.curso.usuarios.Usuario;
import com.exemple.curso.usuarios.UsuarioRequestDTO;
import com.exemple.curso.usuarios.UsuarioResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        var autenticacao = authenticationManager.authenticate(token);

        var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody @Valid UsuarioRequestDTO body, UriComponentsBuilder uriComponentsBuilder){

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(this.usuarioService.create(body));

        var uri = uriComponentsBuilder.path("/create/{id}").buildAndExpand(usuarioResponseDTO.id()).toUri();

        return ResponseEntity.created(uri).body(usuarioResponseDTO);

    }

}
