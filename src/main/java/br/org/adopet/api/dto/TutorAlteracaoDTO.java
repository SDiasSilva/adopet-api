package br.org.adopet.api.dto;

import br.org.adopet.api.model.Tutor;
import jakarta.validation.constraints.NotNull;

public record TutorAlteracaoDTO(@NotNull Long id, String foto, String nome, String telefone, String cidade, String sobre) {
	public TutorAlteracaoDTO(Tutor tutor) {
		this(tutor.getId(), tutor.getFoto(), tutor.getNome(), tutor.getTelefone(), tutor.getCidade(), tutor.getSobre());
	}
}
