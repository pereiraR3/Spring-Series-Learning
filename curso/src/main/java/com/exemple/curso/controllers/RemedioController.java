package com.exemple.curso.controllers;

import java.util.List;

import com.exemple.curso.remedio.*;
import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/remedios")
public class RemedioController {
	
	@Autowired
	private RemedioRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastradosRemedio dados, UriComponentsBuilder uriBuilder) {

		var remedio = new Remedio(dados);

		repository.save(remedio);

		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();

		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));
	}
	
	@GetMapping
	public ResponseEntity<List<DadosListagemRemedio>> listar(){
		var lista = repository.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoRemedio> detalhar(@PathVariable Long id){
		var remedio = repository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@PutMapping
	@Transactional
		public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody  @Valid DadosAtualizarRemedio dados){
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarRemedio(dados);

		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id){
		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar(@PathVariable Long id){
		var remedio = repository.getReferenceById(id);
		remedio.setAtivo(false);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("ativar/{id}")
	@Transactional
	public ResponseEntity<Void> ativar(@PathVariable Long id){
		var remedio = repository.getReferenceById(id);
		remedio.setAtivo(true);

		return ResponseEntity.noContent().build();
	}


}
