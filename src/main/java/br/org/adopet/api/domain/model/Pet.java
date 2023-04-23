package br.org.adopet.api.domain.model;

import java.time.LocalDate;
import br.org.adopet.api.domain.dto.PetAlteracaoDTO;
import br.org.adopet.api.domain.dto.PetCadastroDTO;
import br.org.adopet.api.infra.exception.AdocaoException;
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
		this.abrigo.adicionarPet(this);
		this.nome = dadosPet.nome();
		this.porte = porte;
		this.porte.adicionarPet(this);
		this.descricao = dadosPet.descricao();
		this.dataNascimento = dadosPet.dataNascimento();
		this.foto = dadosPet.foto();
	}
	
	public Boolean isAdotado() {
		return this.adotado;
	}
	
	public void adotar() {
		if(isAdotado()) {
			throw new AdocaoException(this.id);
		}
		this.adotado = true;
	}

	public void atualizarInformacoes(PetAlteracaoDTO dadosPet, Porte porte) {
		if(dadosPet.nome() != null && !dadosPet.nome().trim().isBlank()) {
			this.nome = dadosPet.nome();
		}
		if(porte != null) {
			this.porte = porte;
		}
		if(dadosPet.descricao() != null && !dadosPet.descricao().trim().isBlank()) {
			this.descricao = dadosPet.descricao();
		}
		if(dadosPet.dataNascimento() != null) {
			this.dataNascimento = dadosPet.dataNascimento();
		}
		if(dadosPet.foto() != null && !dadosPet.foto().trim().isBlank()) {
			this.foto = dadosPet.foto();
		}
	}

	public void cancelarAdocao() {
		if(isAdotado()) {
			this.adotado = false;
		}
	}

}
