package br.org.adopet.api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "funcoes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Funcao {
	@Id @GeneratedValue
	private Long id;
	private String nome;
}
