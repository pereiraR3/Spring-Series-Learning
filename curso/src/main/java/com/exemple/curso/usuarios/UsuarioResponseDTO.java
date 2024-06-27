package com.exemple.curso.usuarios;

public record UsuarioResponseDTO(

        Long id,

        String login,

        String senha

) {

    public UsuarioResponseDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getSenha()
        );
    }

}
