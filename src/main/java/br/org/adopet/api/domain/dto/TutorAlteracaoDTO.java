package br.org.adopet.api.domain.dto;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TutorAlteracaoDTO(
		@NotNull 
		Long id, 
		@URL
		String foto, 
		String nome, 
		@Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "O formato do telefone está inválido")
		String telefone,
		@JsonProperty("cidade_id")
		Long cidadeId,
		String sobre) {
}
