package com.algaworks.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.cobranca.model.CobrancaModel;


public interface Cobrancas extends JpaRepository<CobrancaModel, Long> {

	public List<CobrancaModel> findByDescricaoContaining(String descricao);

}
