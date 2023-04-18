package br.org.adopet.api.domain.dto;

import br.org.adopet.api.domain.model.Estado;

public record EstadoListagemDTO(Long id, String sigla, String nome) {

	public EstadoListagemDTO(Estado estado) {
		this(estado.getId(), estado.getSigla(), estado.getNome());
	}

}
