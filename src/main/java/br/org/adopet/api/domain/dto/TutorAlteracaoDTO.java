package br.org.adopet.api.domain.dto;

import br.org.adopet.api.domain.model.Cidade;
import br.org.adopet.api.domain.model.Tutor;
import jakarta.validation.constraints.NotNull;

public record TutorAlteracaoDTO(@NotNull Long id, String foto, String nome, String telefone, Cidade cidade, String sobre) {
	public TutorAlteracaoDTO(Tutor tutor) {
		this(tutor.getId(), tutor.getFoto(), tutor.getContato().getNome(), tutor.getContato().getTelefone(), tutor.getCidade(), tutor.getSobre());
	}
}
