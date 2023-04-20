package br.org.adopet.api.domain.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.org.adopet.api.domain.model.Adocao;

public record AdocaoDetalhamentoDTO(Long id, @JsonProperty("pet_id") Long petId, @JsonProperty("tutor_id") Long tutorId, LocalDate data) {

	public AdocaoDetalhamentoDTO(Adocao adocao) {
		this(adocao.getId(), adocao.getPet().getId(), adocao.getTutor().getId(), adocao.getData());
	}

}
