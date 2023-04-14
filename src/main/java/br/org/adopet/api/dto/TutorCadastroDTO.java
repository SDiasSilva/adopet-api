package br.org.adopet.api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TutorCadastroDTO(
		@NotBlank 
		String nome, 
		
		@NotBlank @Email @Column(unique = true)
		String email,
		
		@NotBlank 
		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
		message = "A senha deve conter pelo menos 8 caracteres, com pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.") 
		String senha) {

}
