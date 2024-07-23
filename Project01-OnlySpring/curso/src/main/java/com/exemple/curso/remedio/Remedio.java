package com.exemple.curso.remedio;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Remedio")
@Entity(name = "Remedios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Remedio {
	
	public Remedio(DadosCadastradosRemedio dados) {

		this.ativo = true;
		this.nome = dados.nome(); 
		this.via = dados.via();
		this.lote = dados.lote();
		this.quantidade = dados.quantidade();
		this.validade = dados.validade();
		this.laboratorio = dados.laboratorio();
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean ativo;

	private String nome; 
	
	@Enumerated(EnumType.STRING)
	private Via via;
	
	private String lote;
	
	private int quantidade;
	
	private LocalDate validade;
	
	@Enumerated(EnumType.STRING)
	private Laboratorio laboratorio;

	public void atualizarRemedio(@Valid DadosAtualizarRemedio dados){

		if(dados.nome() != null){
			this.nome = dados.nome();
		}

		if(dados.via() != null){
			this.via = dados.via();
		}

		if(dados.laboratorio() != null){
			this.laboratorio = dados.laboratorio();
		}

	}

}
