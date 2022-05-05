package com.example.farmacia.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity 
// isso indica que a classe é uma entidade, é utilazada pra criar a tabela de dados no db
@Table(name = "tb_categorias")
// indica/define o nome da tabela no db
public class Categoria {
	
	@Id /*PK - PRIMARY KEY 
	indica que o atributo id é a chave primaria da tabela */
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO _INCREMENT
	/* 	@GenerateValue - Indica que a PK será gerada automaticamente pelo db(banco de dados)
		strategy - indica a estrategia, ou seja, qual estrategia que a PK irá utilizar para ser gerada
		GenerationType.IDENTITY - indica que a estratégia de criação é a IDENTITY */
 	private Long id;
	
	@NotBlank (message = "O atributo nome é obrigatório ")
	//indica que um atributo não pode ser nulo e também não pode ser deixado em branco (vazio).
	@Size(min = 5, message = "Este atributo deve conter no minimo 5 caracteres")
	// tem a função de definir o tamanho minimo e máximo de caracteres de um atributo String.
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@OneToMany (mappedBy = "categoria", cascade = CascadeType.REMOVE)
	// Classe Categoria terá um relacionamento do tipo One To Many (Um para Muitos) com a Classe Produto
	@JsonIgnoreProperties("categoria")
	private List<Produto> produto;

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
