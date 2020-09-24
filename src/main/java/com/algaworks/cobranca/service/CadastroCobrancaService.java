package com.algaworks.cobranca.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.CobrancaModel;
import com.algaworks.cobranca.model.StatusCobranca;
import com.algaworks.cobranca.repository.Cobrancas;
import com.algaworks.cobranca.repository.filter.CobrancaFilter;


@Service
public class CadastroCobrancaService {

	@Autowired
	private Cobrancas cobrancas;
	
	public void salvar(CobrancaModel cobrancaModel) {
		
		try {
			
				cobrancas.save(cobrancaModel);
			
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido!");
		}
	}
	
	public String receber(Long codigo) {
		
		//CobrancaModel cobrancaModel = cobrancas.findOne(codigo);
		//cobrancaModel.setStatus(StatusCobranca.RECEBIDO);
		
		//cobrancas.save(cobrancaModel);
		
		return StatusCobranca.RECEBIDO.getDescricao();
	}
	
	
	public void excluir(Long codigo) {
		
		cobrancas.deleteById(codigo);
	}

	
	public List<CobrancaModel> filtrar(CobrancaFilter filtro){
		
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		
		return cobrancas.findByDescricaoContaining(descricao);
	}
}