package br.org.adopet.api.domain.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record PetAlteracaoDTO(
		@NotNull
		Long id,
		String nome,
		@JsonProperty("porte_id")
		Long porteId,
		String descricao,
		@JsonProperty("data_nascimento")
		LocalDate dataNascimento,
		@URL
		String foto) {

}
