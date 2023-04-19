package br.org.adopet.api.domain.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetCadastroDTO(
		@NotNull @JsonProperty("abrigo_id")
		Long abrigoId,
		@NotBlank
		String nome,
		@NotNull
		Long porteId,
		@NotBlank
		String descricao,
		@NotNull
		LocalDate dataNascimento,
		@NotBlank
		String foto) {
}
