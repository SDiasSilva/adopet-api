package br.org.adopet.api.domain.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adocoes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Adocao {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private Pet pet;
	@ManyToOne
	private Tutor tutor;
	private LocalDate data;
	
	public Adocao(Pet pet, Tutor tutor) {
		this.pet = pet;
		this.pet.adotar();
		this.tutor = tutor;
		this.tutor.adicionarAdocao(this);
		this.data = LocalDate.now();
	}

	public void cancelar() {
		this.tutor.removerAdocao(this);
		this.pet.cancelarAdocao();
	}
}
