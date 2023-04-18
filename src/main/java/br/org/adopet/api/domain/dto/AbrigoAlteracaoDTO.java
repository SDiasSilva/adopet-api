package br.org.adopet.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record AbrigoAlteracaoDTO(
		@NotNull
		Long id,
		String nome,
		String email,
		String telefone,
		Long cidadeId) {

}
