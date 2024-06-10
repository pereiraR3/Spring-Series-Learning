package com.exemple.curso.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.curso.remedio.DadosCadastradosRemedio;
import com.exemple.curso.remedio.DadosListagemRemedio;
import com.exemple.curso.remedio.Remedio;
import com.exemple.curso.remedio.RemedioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")
public class RemedioController {
	
	@Autowired
	private RemedioRepository repository;
	
	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastradosRemedio dados) {
	
		repository.save(new Remedio(dados));
		
	}
	
	@GetMapping
	@Transactional
	public List<DadosListagemRemedio> listar(){
		return repository.findAll().stream().map(DadosListagemRemedio::new).toList();
	}

}
