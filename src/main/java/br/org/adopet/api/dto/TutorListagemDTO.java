package br.org.adopet.api.dto;

import br.org.adopet.api.model.Tutor;

public record TutorListagemDTO(Long id, String nome, String telefone, String email, String cidade, String sobre) {
	public TutorListagemDTO(Tutor tutor) {
		this(tutor.getId(), tutor.getNome(), tutor.getTelefone(), tutor.getEmail(), tutor.getCidade(), tutor.getSobre());
	}
}
