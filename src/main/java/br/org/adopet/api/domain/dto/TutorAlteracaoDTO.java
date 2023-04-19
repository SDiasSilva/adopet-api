package br.org.adopet.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record TutorAlteracaoDTO(@NotNull Long id, String foto, String nome, String telefone, @JsonProperty("cidade_id") Long cidadeId, String sobre) {
}
