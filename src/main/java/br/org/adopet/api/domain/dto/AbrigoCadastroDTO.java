package br.org.adopet.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AbrigoCadastroDTO (
		@NotBlank
		String nome, 
		@NotBlank @Email
		String email, 
		@NotBlank 
		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
		message = "A senha deve conter pelo menos 8 caracteres, com pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.") 
		String senha,
		@NotBlank @Pattern(regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}", message = "O formato do telefone está inválido")
		String telefone, 
		@NotNull @JsonProperty("cidade_id")
		Long cidadeId){

}
