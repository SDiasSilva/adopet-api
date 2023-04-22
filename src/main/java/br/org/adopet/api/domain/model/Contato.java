package br.org.adopet.api.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contato {
	private String nome;
	private String telefone;
	
	public Contato(String nome) {
		this.nome = nome;
	}
}
