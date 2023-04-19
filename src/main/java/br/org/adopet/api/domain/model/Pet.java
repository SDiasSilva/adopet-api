package br.org.adopet.api.domain.model;

import java.time.LocalDate;

import br.org.adopet.api.domain.dto.PetCadastroDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pet {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Abrigo abrigo;
	private String nome;
	@ManyToOne
	private Porte porte;
	private String descricao;
	private Boolean adotado = false;
	private LocalDate dataNascimento;
	private String foto;
	
	public Pet(PetCadastroDTO dadosPet, Abrigo abrigo, Porte porte) {
		this.abrigo = abrigo;
		this.nome = dadosPet.nome();
		this.porte = porte;
		this.descricao = dadosPet.descricao();
		this.dataNascimento = dadosPet.dataNascimento();
		this.foto = dadosPet.foto();
	}
	
	public void adotar() {
		this.adotado = true;
	}

}
