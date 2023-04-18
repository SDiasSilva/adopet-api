package br.org.adopet.api.domain.dto;

import br.org.adopet.api.domain.model.Cidade;

public record CidadeListagemDTO(Long id, String nome, EstadoListagemDTO estado) {

	public CidadeListagemDTO(Cidade cidade) {
		this(cidade.getId(), cidade.getNome(), new EstadoListagemDTO(cidade.getEstado()));
	}

}
