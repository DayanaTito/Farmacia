package com.example.farmacia.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.farmacia.model.Produto;

@Repository
// indica que a Interface é do tipo tipo repositório
// é responsável pela comunicação com o Banco de dados através dos métodos
// padrão e das Method Queries (Métodos Personalizados), que são as consultas personalizadas 
// criadas através de palavras chave que representam as instruções da linguagem SQL.
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	// Method Query equivalente a instrução SQL: SELECT * FROM tb_produtos where nome like "%nome%";
	public List<Produto> findAllByNomeOrLaboratorio(String nome, String laboratioro);
	// Select * from tb_produtos where nome or laborario = aleatorio;
	public List<Produto> findAllByPrecoBetween(@PathVariable BigDecimal inicio, @PathVariable BigDecimal fim);
	// Select * from tb_produtos where nome BETWEEN laborario = aleatorio;
	public List<Produto> findAllByNomeAndLaboratorio(String nome, String laboratioro);
}
