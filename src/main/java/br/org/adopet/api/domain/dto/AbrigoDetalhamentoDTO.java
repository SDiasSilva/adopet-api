package br.org.adopet.api.domain.dto;

import br.org.adopet.api.domain.model.Abrigo;

public record AbrigoDetalhamentoDTO(Long id, String nome, String email, String telefone, String cidade, String estado) {
	public AbrigoDetalhamentoDTO(Abrigo abrigo){
		this(abrigo.getId(), abrigo.getNome(), abrigo.getEmail(), abrigo.getTelefone(), abrigo.getNomeCidade(), abrigo.getEstado());
	}
}
