package com.exemple.curso.remedio;

import java.time.LocalDate;

public record DadosDetalhamentoRemedio(

        Long id,
        Boolean ativo,
        String nome,
        Via via,
        String lote,
        int quantidade,
        LocalDate validade,
        Laboratorio laboratorio

) {

    public DadosDetalhamentoRemedio(Remedio remedio){
        this(
                remedio.getId(),
                remedio.getAtivo(),
                remedio.getNome(),
                remedio.getVia(),
                remedio.getLote(),
                remedio.getQuantidade(),
                remedio.getValidade(),
                remedio.getLaboratorio()
        );
    }

}
