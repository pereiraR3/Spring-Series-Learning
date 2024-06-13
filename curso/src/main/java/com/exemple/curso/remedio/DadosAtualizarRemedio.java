package com.exemple.curso.remedio;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarRemedio(

        @NotNull
        Long id,

        String nome,

        Via via,

        Laboratorio laboratorio

) {
}
