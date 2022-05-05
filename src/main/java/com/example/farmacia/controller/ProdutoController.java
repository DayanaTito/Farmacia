package com.example.farmacia.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.farmacia.model.Produto;
import com.example.farmacia.repository.CategoriaRepository;
import com.example.farmacia.repository.ProdutoRepository;

@RestController
// indica que a Classe é uma RestController, ou seja, 
// é responsável por responder todas as requisições http enviadas para um  endereço definido na anotação @RequestMapping
@RequestMapping("/produtos")
// indica o endereço que a controladora responderá as requisições 
@CrossOrigin(origins = "*", allowedHeaders = "*")
/* indica que a classe controladora permitirá o 
* recebimento de requisições realizadas de fora do domínio (localhost, em nosso caso) ao qual 
* ela pertence. Essa anotação é essencial para que o front-end (Angular ou React), tenha
* acesso à nossa API (O termo técnico é consumir a API)
* Para as versões mais recentes do Angular e do React, é necessário configurar esta anotação 
* com os seguintes parâmetros: @CrossOrigin(origins = "*", allowedHeaders = "*") 
*  
* Esta anotação, além de liberar todas as origens (origins), libera também todos os parâmetros
* do cabeçalho das requisições (allowedHeaders).
* 
* Em produção, o * é substituido pelo endereço de domínio (exemplo: www.meudominio.com) do
* Frontend*/
public class ProdutoController {
	
	@Autowired 
	// Injeção de dependência a  implementação 
	// utilizada pelo  Spring  Framework  de  aplicar  a  Inversão  de  Controle  quando  for  necessário
	/*
	 a classe controladora cria um ponto de injeção da interface ProdutoRepository, 
	 * e quando houver a necessidade o Spring Framework irá criar uma instância (objeto) desta interface
	 * permitindo o uso de todos os métodos (padrão ou personalizados da Interface ProdutoRepository) */
	private ProdutoRepository produtoRepository;
	
	@Autowired 
	// Injeção de dependência 
	private CategoriaRepository categoriaRepository;
	
	@GetMapping // metodo get para listar todos os produtos
	//indica que o método abaixo responderá todas as requisições do tipo GET que forem enviadas no endereço /produtos
	public ResponseEntity <List<Produto>> getAll(){
		return ResponseEntity.ok(produtoRepository.findAll()); // SELECT * FROM TB_PRODUTOS
	}
	
	@GetMapping("/{id}") // metodo get Listar produto por id
	public ResponseEntity <Produto> getById(@PathVariable Long id){
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/nome/{nome}")//Consultar produtos por nome
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping //Metodo para criar produto
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto){
		if (categoriaRepository.existsById(produto.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));

		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping //Atualizar/Editar um produto
	public ResponseEntity<Produto> putProduto(@RequestBody Produto produto){
		if (produtoRepository.existsById(produto.getId())) {
			
			if (categoriaRepository.existsById(produto.getCategoria().getId())) 
				return ResponseEntity.ok(produtoRepository.save(produto));
			else
				return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/nome/{nome}/oulab/{laboratorio}")// consultar por nome ou lab
	public ResponseEntity<List<Produto>> getByNomeOuLab(@PathVariable String nome, @PathVariable String laboratorio){
		return ResponseEntity.ok(produtoRepository.findAllByNomeOrLaboratorio(nome, laboratorio));
	}
	
	@GetMapping("/nome/{nome}/elab/{laboratorio}")// consultar por nome e laboratorio
	public ResponseEntity<List<Produto>> getNomeELab(@PathVariable String nome, @PathVariable String laboratorio){
		return ResponseEntity.ok(produtoRepository.findAllByNomeAndLaboratorio(nome, laboratorio));
	}
	
	@GetMapping("/preco_1/{inicio}/preco_2/{fim}") // Consultar entre dois preços BETWEEN
		public ResponseEntity<List<Produto>> getByPrecoEntre( @PathVariable BigDecimal inicio,@PathVariable BigDecimal fim  ){
			return ResponseEntity.ok(produtoRepository.findAllByPrecoBetween(inicio, fim));
	}
	
	@DeleteMapping("/{id}") //Deletar produto por id
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		return produtoRepository.findById(id)
				.map(resposta -> {
					produtoRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	
}
