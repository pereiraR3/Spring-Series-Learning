package com.exemple.curso.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(

        @NotBlank
        String login,

        @NotBlank
        String senha

) {
}
