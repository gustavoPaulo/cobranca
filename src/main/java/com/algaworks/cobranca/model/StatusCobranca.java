package com.algaworks.cobranca.model;

public enum StatusCobranca {

	PENDENTE("Pendente"),
	RECEBIDO("Recebido");
	
	private String descricao;
	
	StatusCobranca(String descricao){
		
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		
		return descricao;
	}
}
