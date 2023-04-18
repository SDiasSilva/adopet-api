package br.org.adopet.api.domain.dto;

import br.org.adopet.api.domain.model.Cidade;
import br.org.adopet.api.domain.model.Tutor;

public record TutorListagemDTO(Long id, String nome, String telefone, String email, Cidade cidade, String sobre) {
	public TutorListagemDTO(Tutor tutor) {
		this(tutor.getId(), tutor.getContato().getNome(), tutor.getContato().getTelefone(), tutor.getContato().getEmail(), tutor.getCidade(), tutor.getSobre());
	}
}
