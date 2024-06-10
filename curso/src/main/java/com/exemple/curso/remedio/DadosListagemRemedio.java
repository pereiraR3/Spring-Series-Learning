package com.exemple.curso.remedio;

import java.time.LocalDate;

public record DadosListagemRemedio(
		
		String nome, 
		Via via, 
		String lote,
		Laboratorio laboratorio, 
		LocalDate validade
		
		) {
	
		public DadosListagemRemedio(Remedio remedio) {
			this(remedio.getNome(), remedio.getVia(), remedio.getLote(), remedio.getLaboratorio(), remedio.getValidade());
		
	}
	
}