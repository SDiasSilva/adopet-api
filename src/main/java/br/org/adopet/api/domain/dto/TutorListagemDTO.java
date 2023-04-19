package br.org.adopet.api.domain.dto;

import br.org.adopet.api.domain.model.Tutor;

public record TutorListagemDTO(Long id, String foto, String nome, String telefone, String email, String cidade,
		String estado, String sobre) {
	public TutorListagemDTO(Tutor tutor) {
		this(tutor.getId(), tutor.getFoto(), tutor.getNome(), tutor.getTelefone(), tutor.getEmail(),
				tutor.getNomeCidade(), tutor.getEstado(), tutor.getSobre());
	}
}
