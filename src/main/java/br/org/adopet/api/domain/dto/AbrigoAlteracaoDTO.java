package br.org.adopet.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AbrigoAlteracaoDTO(
		@NotNull
		Long id,
		String nome,
		@Email
		String email,
		@Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "O formato do telefone está inválido")
		String telefone,
		@JsonProperty("cidade_id")
		Long cidadeId) {

}
