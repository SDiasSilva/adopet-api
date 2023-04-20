package br.org.adopet.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record AdocaoCadastro(
		@NotNull @JsonProperty("pet_id")
		Long petId,
		@NotNull @JsonProperty("tutor_id")
		Long tutorId) {

}
