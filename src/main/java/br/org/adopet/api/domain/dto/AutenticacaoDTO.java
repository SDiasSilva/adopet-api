package br.org.adopet.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutenticacaoDTO(
		@NotBlank @Email
		String email,
		@NotBlank 
		String senha) {

}
