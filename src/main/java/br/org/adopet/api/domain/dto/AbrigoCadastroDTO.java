package br.org.adopet.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AbrigoCadastroDTO (
		@NotBlank
		String nome, 
		@NotBlank @Email
		String email, 
		@NotBlank
		String telefone, 
		@NotNull
		Long cidadeId){

}
